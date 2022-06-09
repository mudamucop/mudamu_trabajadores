package com.Mudamu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Mudamu.model.Medico;
import com.Mudamu.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

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
			url = "redirect:/pacPage";
		}
		return url;
	}
}