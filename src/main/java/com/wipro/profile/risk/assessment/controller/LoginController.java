package com.wipro.profile.risk.assessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.wipro.profile.risk.assessment.model.User;

@Controller
public class LoginController {
	
	@RequestMapping(value = {"/", "login"}, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}
	
	/*@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView model = new ModelAndView();
		
		User user = new User();
		model.addObject("user", user);
		model.setViewName("registration");
		return model;
	} */
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		
		ModelAndView model = new ModelAndView();
		
		
	}
	
	
}
