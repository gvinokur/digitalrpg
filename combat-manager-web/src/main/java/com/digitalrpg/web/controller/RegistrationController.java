package com.digitalrpg.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.digitalrpg.web.controller.model.UserRegistrationVO;
import com.digitalrpg.web.service.RegistrationService;
import com.google.common.collect.ImmutableMap;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String showRegistrationPage() {
		return "register";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView registerUser(@Valid @ModelAttribute("user") UserRegistrationVO user, BindingResult bindingResult, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		String remoteAddr = request.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LfGTOgSAAAAABbQhBkr36lp24ucA_NVEIJQKBDq");
		ReCaptchaResponse answer = reCaptcha.checkAnswer(remoteAddr, user.getRecaptcha_challenge_field(), user.getRecaptcha_response_field());
		if(!answer.isValid()) {
			bindingResult.rejectValue("recaptcha_response_field", "captcha.code.invalid", "Invalid captcha code");
		}
		if(bindingResult.hasErrors()) {
			return new ModelAndView("register", bindingResult.getModel());
		}
		
		String contextPath = "http://" + request.getServerName() + 
				(request.getContextPath()!=null && !request.getContextPath().isEmpty()? "/" + request.getContextPath(): "");
		registrationService.registerUser(user.getUsername(), user.getEmail(), user.getPassword(), contextPath);
		//TODO: Send activationToken by email.
		redirectAttributes.addFlashAttribute("message", "Registration was Successfull, an email was sent to " + user.getEmail() + " with activation details");
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(value= "/confirm", method = RequestMethod.GET)
	public String activateUser(@RequestParam String username, @RequestParam String activationToken, final RedirectAttributes redirectAttributes) {
		registrationService.activate(username, activationToken);
		redirectAttributes.addFlashAttribute("message", "Account for " + username + " is now Active");
		return "redirect:/login";
	}
	
	@RequestMapping(value= "/check-username/{username}", method = RequestMethod.GET)
	@ResponseBody
	public Map validateUsernameAvailability(@PathVariable String username) {
		Boolean isAvailable = registrationService.validateUsernameAvailability(username);
		return ImmutableMap.of("available", isAvailable);
	}
	
	
}
