package com.Mudamu.controller;

import com.Mudamu.model.CitaMedico;
import com.Mudamu.model.Medico;
import com.Mudamu.model.Prediccion;
import com.Mudamu.service.LoginService;

import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
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
				model.addAttribute("citas", filtrar((List<CitaMedico>)userService.getCitas(user)));
				model.addAttribute("section", "active");
			} else if (user.getTipo().toLowerCase().equals("administrativo")) {
				model.addAttribute("administrativo", "active");
				model.addAttribute("nuevasCitas", userService.getNuevasCitas());
				model.addAttribute("citas", filtrar((List<CitaMedico>)userService.getCitasAdministrativo()));
			} else {
				model.addAttribute("userForm", new Medico());
				model.addAttribute("admin", "active");
			}
		} else {
			return "redirect:/login";
		}

		return url;
	}

	private List<CitaMedico> filtrar(List<CitaMedico> lista) {
		List<CitaMedico> filteredLista = new ArrayList<>();

		for (CitaMedico cita : lista) {
			if (cita.getFecha_hora() != null)
				filteredLista.add(cita);
		}
		return filteredLista;
	}

	@GetMapping("/predPag")
	public String paginaPredicciones(Model model) throws Exception {
		String url = "";
		HttpServletResponse response;

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Medico user = userService.loadUserByUsername(((UserDetails) principal).getUsername());

		if (user.getTipo() != null) {
			if (user.getTipo().toLowerCase().equals("medico")) {
				model.addAttribute("predicciones", "active");
				model.addAttribute("predicciones", userService.getPredicciones(user));
				model.addAttribute("section", "active");
				url = "index";
			} else
				url = "404";
		} else
			url = "404";

		return url;
	}

	@GetMapping("/generateCitas")
	public String paginaGenerateCitas(Model model) throws Exception {
		String url = "";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Medico user = userService.loadUserByUsername(((UserDetails) principal).getUsername());

		if (user.getTipo().toLowerCase().equals("administrativo")) {
			// Cita cita = new Cita();
			model.addAttribute("administrativo", "active");
			model.addAttribute("generate", "active");
			url = "index";
		} else
			url = "404";

		return url;
	}

	@PostMapping("/busqueda")
	public String peticionBusqueda(Model model, @RequestBody String data) throws Exception{
		System.out.println(data);
		//model.addAttribute("news", filtrar((List<CitaMedico>)userService.getCitasAdministrativo()));

		return "redirect:/generateCitas";
	}

	@PostMapping("/requestCita")
	public String requestCita(Model model, @RequestBody String data) {
		Integer catID = 0;

		String separate = data.split("=")[1];

		String cate = data.split("=")[1].split("%2F")[1];

		if (cate.toLowerCase().equals("leve"))
			catID = 1;
		else if (cate.toLowerCase().equals("moderado"))
			catID = 2;
		else if (cate.toLowerCase().equals("grave"))
			catID = 3;
		else
			catID = 4;

		userService.updateCitaSolicitada(Integer.parseInt(data.split("=")[1].split("%2F")[0]), catID);

		return "redirect:/predPag";
	}
}
