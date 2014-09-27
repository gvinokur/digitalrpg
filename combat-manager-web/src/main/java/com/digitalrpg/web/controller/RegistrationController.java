package com.digitalrpg.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.digitalrpg.domain.dao.UserDao;
import com.digitalrpg.domain.model.User;
import com.digitalrpg.domain.model.messages.InviteToCampaignMessage;
import com.digitalrpg.domain.model.messages.Message;
import com.digitalrpg.web.controller.model.InviteMessageVO;
import com.digitalrpg.web.controller.model.MessageVO;
import com.digitalrpg.web.controller.model.UserRegistrationVO;
import com.digitalrpg.web.service.MessageService;
import com.digitalrpg.web.service.RegistrationService;
import com.google.common.collect.ImmutableMap;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/heading")
    public ModelAndView getHeading(HttpServletRequest request, HttpServletResponse response) {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (savedRequest != null) {
            String[] messages = savedRequest.getParameterValues("messageId");
            if (messages != null && messages.length > 0) {
                Message message = messageService.getMessage(Long.valueOf(messages[0]));
                if (message != null && message instanceof InviteToCampaignMessage) {
                    modelMap.put(
                            "message",
                            String.format(
                                    "<p><strong>%s</strong> has invited you to join the campaign \"%s\".</p> <small>Use the form below to sign up or login and claim your spot at the table.</small>",
                                    message.getFrom().getName(), ((InviteToCampaignMessage) message).getCampaign().getName()));
                }
            }
        }

        return new ModelAndView("register-heading", modelMap);

    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(HttpServletRequest request, HttpServletResponse response) {
        UserRegistrationVO userRegistrationVO = new UserRegistrationVO();

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null) {
            String[] emails = savedRequest.getParameterValues("email");
            if (emails.length > 0) {
                userRegistrationVO.setEmail(emails[0]);
            }
        }

        return new ModelAndView("register", "user", userRegistrationVO);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid @ModelAttribute("user") UserRegistrationVO userRegistrationVO, BindingResult bindingResult,
            HttpServletRequest request, final RedirectAttributes redirectAttributes, HttpServletResponse response) throws ServletException,
            IOException {
        String remoteAddr = request.getRemoteAddr();
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LfGTOgSAAAAABbQhBkr36lp24ucA_NVEIJQKBDq");
        ReCaptchaResponse answer =
                reCaptcha.checkAnswer(remoteAddr, userRegistrationVO.getRecaptcha_challenge_field(),
                        userRegistrationVO.getRecaptcha_response_field());
        if (!answer.isValid()) {
            bindingResult.rejectValue("recaptcha_response_field", "captcha.code.invalid", "Invalid captcha code");
        }
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register", bindingResult.getModel());
        }

        String contextPath =
                "http://" + request.getServerName()
                        + (request.getContextPath() != null && !request.getContextPath().isEmpty() ? "/" + request.getContextPath() : "");

        User user =
                registrationService.registerUser(userRegistrationVO.getUsername(), userRegistrationVO.getEmail(),
                        userRegistrationVO.getPassword(), contextPath);

        redirectAttributes.addFlashAttribute("form_message", "Welcome to DigitalRPG");
        // Automatic activation and login
        registrationService.activate(user.getName(), user.getActivationToken());
        authenticateUserAndSetSession(userRegistrationVO, request);

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        String redirectUrl = null;
        if (savedRequest != null) {
            redirectUrl = "redirect:" + savedRequest.getRedirectUrl();
        } else {
            redirectUrl = "redirect:/home";
        }
        return new ModelAndView(redirectUrl);
    }

    private Authentication authenticateUserAndSetSession(UserRegistrationVO userRegistrationVO, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userRegistrationVO.getUsername(), userRegistrationVO.getPassword());

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        return authenticatedUser;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String activateUser(@RequestParam String username, @RequestParam String activationToken,
            final RedirectAttributes redirectAttributes) {
        registrationService.activate(username, activationToken);
        redirectAttributes.addFlashAttribute("form_message", "Account for " + username + " is now Active");
        return "redirect:/login";
    }

    @RequestMapping(value = "/check-username/{username}", method = RequestMethod.GET)
    @ResponseBody
    public Map validateUsernameAvailability(@PathVariable String username) {
        Boolean isAvailable = registrationService.validateUsernameAvailability(username);
        return ImmutableMap.of("available", isAvailable);
    }


}
