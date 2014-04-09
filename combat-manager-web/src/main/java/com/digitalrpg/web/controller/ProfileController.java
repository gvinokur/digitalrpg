package com.digitalrpg.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "/profile")
@Controller
public class ProfileController {

	@RequestMapping(method = RequestMethod.GET)
	public String showProfile() {
		return "profile";
	}
	
}
