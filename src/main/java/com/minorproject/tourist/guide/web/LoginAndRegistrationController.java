package com.minorproject.tourist.guide.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.minorproject.tourist.guide.model.User;
import com.minorproject.tourist.guide.service.SecurityService;
import com.minorproject.tourist.guide.service.UserService;

@Controller
public class LoginAndRegistrationController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
    
	 // To show registration form
 	@GetMapping("/registration")
 	public String registration(Model model) {

 		if (securityService.isAuthenticated()) {
 			return "redirect:/";
 		}

 		model.addAttribute("userForm", new User());

 		return "registration";
 	}

 	// To save form from registration
 	@PostMapping("/registration")
 	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
 		userValidator.validate(userForm, bindingResult);

 		if (bindingResult.hasErrors()) {
 			return "registration";
 		}

 		userService.save(userForm);

 		return "redirect:/login?register=true";
 	}

 	// Login Logic
 	@GetMapping(value = {"/","/login"})
 	public String login(Model model, String error, String logout) {
 		if (securityService.isAuthenticated()) {
 			return "redirect:/";
 		}
 		/*
		 * if (error != null) model.addAttribute("error",
		 * "Your username and password is invalid.");
		 * 
		 * if (logout != null) model.addAttribute("message",
		 * "You have been logged out successfully.");
		 */
 		return "login";
 	}
}
