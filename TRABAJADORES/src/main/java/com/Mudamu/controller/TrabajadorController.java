package com.Mudamu.controller;

import com.Mudamu.model.Medico;
import com.Mudamu.model.Prediccion;
import com.Mudamu.service.LoginService;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class TrabajadorController {

	@Autowired
	LoginService userService;

	@GetMapping("/pacPage")
	public String paginaPrincipal(Model model) throws Exception {
		String url = "index";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Medico user = userService.loadUserByUsername(((UserDetails) principal).getUsername());

		if (user.getTipo() != null) {
			if (user.getTipo().toLowerCase().equals("medico")) {
				model.addAttribute("citas", "active");
				model.addAttribute("citas", userService.getCitas(user));
				model.addAttribute("section", "active");
			} else if (user.getTipo().toLowerCase().equals("administrativo")) {
				model.addAttribute("administrativo", "active");
				model.addAttribute("citas", userService.getCitasAdministrativo());
				//model.addAttribute("citas", "active");
			} else {
				model.addAttribute("userForm", new Medico());
				model.addAttribute("admin", "active");
			}
		} else {
			return "redirect:/login";
		}

		return url;
	}

	@GetMapping("/predPag")
	public String paginaPredicciones(Model model) throws Exception {
		String url ="";
		HttpServletResponse response;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Medico user = userService.loadUserByUsername(((UserDetails) principal).getUsername());

		if (user.getTipo().toLowerCase().equals("medico")) {
			model.addAttribute("predicciones", "active");
			model.addAttribute("predicciones", userService.getPredicciones(user));
			url = "index";
		}
		else url = "404";

		return url;
	}

	@GetMapping("/generateCitas")
	public String paginaGenerateCitas(Model model) throws Exception{
		String url="";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Medico user = userService.loadUserByUsername(((UserDetails) principal).getUsername());

		if (user.getTipo().toLowerCase().equals("administrativo")){
			//Cita cita = new Cita();
			model.addAttribute("generate", "active");
			url = "index";
		} else url = "404";

		return url;
	}
}
