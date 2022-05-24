package com.Mudamu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Mudamu.model.Medico;
import com.Mudamu.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	LoginService userService;

	@GetMapping({ "/", "/login" })
	public String index() {
		return "login";
	}

	@GetMapping("/signin")
	public String signin(Model model) {
		return "login";
	}

	@GetMapping("/passForm")
	public String forgetPassword(ModelMap model) {
		/*
		 * model.addAttribute("userForm", new User());
		 * model.addAttribute("signup", true);
		 */

		System.out.println("sada");

		return "register";
	}

	@GetMapping("/signup")
	public String signup(ModelMap model) {
		model.addAttribute("userForm", new Medico());
		model.addAttribute("signup", true);

		return "register";
	}

	@PostMapping("/signup")
	public String createUser(@ModelAttribute("userForm") Medico user, @ModelAttribute("confirm") String confirm,
			BindingResult result, ModelMap model) {
		String url = "";
		model.addAttribute("userForm", user);
		if (result.hasErrors()) {
			return "register";
		} else {
			try {
				userService.createUser(user);
				

			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
			}
			url = "login";
		}
		return url;
	}
}