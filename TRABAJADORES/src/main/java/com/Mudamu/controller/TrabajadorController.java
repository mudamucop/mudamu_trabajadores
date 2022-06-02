package com.Mudamu.controller;

import com.Mudamu.model.CitaMedico;
import com.Mudamu.model.Medico;
import com.Mudamu.model.Prediccion;
import com.Mudamu.service.LoginService;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.userdetails.UserDetails;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.Mudamu.model.User;

@Controller
public class TrabajadorController {

	@Autowired
	LoginService userService;

	final static String EXCHANGE_NAME = "ciclocompleto";
	ConnectionFactory factory;

	public TrabajadorController() {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setUsername("admin");
		factory.setPassword("password");
	}

	@GetMapping("/pacPage")
	public String paginaPrincipal(Model model) throws Exception {
		String url = "index";

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Medico user = userService.loadUserByUsername(((UserDetails) principal).getUsername());

		if (user.getTipo() != null) {
			if (user.getTipo().toLowerCase().equals("medico")) {
				model.addAttribute("citas", "active");
				model.addAttribute("citas", filtrar((List<CitaMedico>) userService.getCitas(user), true));
				model.addAttribute("section", "active");
			} else if (user.getTipo().toLowerCase().equals("administrativo")) {
				model.addAttribute("administrativo", "active");
				model.addAttribute("nuevasCitas", userService.getNuevasCitas());
				model.addAttribute("citas", filtrar((List<CitaMedico>) userService.getCitasAdministrativo(), true));
			} else {
				model.addAttribute("userForm", new Medico());
				model.addAttribute("admin", "active");
			}
		} else {
			return "redirect:/login";
		}

		return url;
	}

	private List<CitaMedico> filtrar(List<CitaMedico> lista, boolean condicion) {
		List<CitaMedico> filteredLista = new ArrayList<>();

		for (CitaMedico cita : lista) {
			if (cita.getFecha_hora() != null && condicion)
				filteredLista.add(cita);
			else if (cita.getFecha_hora() == null && !condicion)
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
				Channel channel = null;
				try (Connection connection = factory.newConnection()) {

					channel = connection.createChannel();
					channel.exchangeDeclare(EXCHANGE_NAME, "direct");
					String queueName = channel.queueDeclare().getQueue();
					channel.queueBind("medico1", EXCHANGE_NAME, String.valueOf(user.getTrabajadorID()));

					MiConsumer consumer = new MiConsumer(channel);
					boolean autoack = true;
					String tag = channel.basicConsume("medico1", autoack, consumer);

					channel.basicCancel(tag);
					channel.close();

				} catch (IOException | TimeoutException e) {
					e.printStackTrace();
				}
				url = "index";
			} else
				url = "404";
		} else
			url = "404";

		return url;
	}

	public class MiConsumer extends DefaultConsumer {

		public MiConsumer(Channel channel) {
			super(channel);

		}

		@Override
		public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
				throws IOException {

			ByteBuffer byteBuffer = ByteBuffer.wrap(body);
			IntBuffer intBuffer = byteBuffer.asIntBuffer();

			int valor = intBuffer.get();
			System.out.println("Recibido:  " + valor + " suma 13");
		}

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
	public ResponseEntity<String> peticionBusqueda(Model model, @RequestBody String data) throws Exception {
		List<CitaMedico> lista = new ArrayList<>();
		for (CitaMedico ls : (List<CitaMedico>)userService.getNuevasCitas())
			if (ls.getTarjetaSanitaria().equals(data.split("=")[1]))
				lista.add(ls);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");

		return new ResponseEntity<String>(new Gson().toJson(lista), headers, HttpStatus.OK);
	}

	@PostMapping("/crearCita")
	public String crearCita(Model model, @RequestBody String data) throws Exception {
		String[] split = data.split("&");
		String ts = data.split("&")[0].split("=")[1];

		User user = userService.loadByTarjetaUser(ts);
		String fecha_hora = data.split("&")[1].split("=")[1] + " " + data.split("&")[2].split("=")[1].split("%")[0]
				+ ":" + data.split("&")[2].split("=")[1].split("%")[1].split("A")[1];

		// userService.crearCita(fecha_hora, user.getpacienteID());

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
