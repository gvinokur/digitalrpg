package com.digitalrpg.web.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.digitalrpg.domain.model.RecentItem;
import com.digitalrpg.web.service.TransactionalUserService;
import com.digitalrpg.web.service.UserWrapper;

@Controller
@RequestMapping("/lobby")
public class LobbyController {

    @Autowired
    TransactionalUserService userService;

    @ModelAttribute("recentItems")
    public Set<RecentItem> getRecentItems(@AuthenticationPrincipal UserWrapper user) {
        return userService.getRecentItems(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showLobby() {
        return "/lobby";
    }

}
