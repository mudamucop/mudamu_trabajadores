package com.Mudamu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Mudamu.model.CitasMedico;
import com.Mudamu.model.Medico;
import com.Mudamu.model.Prediccion;
import com.Mudamu.model.Predicciones;
import com.Mudamu.rest.CitaRESTClient;
import com.Mudamu.rest.PrediccionRESTClient;
import com.Mudamu.rest.UserRESTClient;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UserRESTClient userRESTClient;

	@Autowired
	PrediccionRESTClient prediccionRESTClient;

	@Autowired
	CitaRESTClient citaRESTClient;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private boolean checkUsernameAvailable(Medico user) throws Exception {
		Medico userFound = userRESTClient.getUserName(user.getUsername());
		if (userFound.getUsername() != null) {
			throw new Exception("Username no disponible");
		}
		return true;
	}


	@Override
	public Medico createUser(Medico user2) throws Exception {
		Medico user = new Medico();
		user.setUsername(user2.getUsername());
		user.setApellido1(user2.getApellido1());
		user.setApellido2(user2.getApellido2());
		user.setNombre(user2.getNombre());
		user.setEmail(user2.getEmail());
		user.setSalt("salt");
		user.setTelefono(user2.getTelefono());
		user.setTipo(user2.getTipo());
		user.setPassword(user2.getPassword());
		if (checkUsernameAvailable(user2)) {
			String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			userRESTClient.postUserXml(user);
		}
		return user;
	}
	
	protected void mapUser(Medico from,Medico to) {
		to.setUsername(from.getUsername());
		/*to.setName(from.getName());
		to.setSurname(from.getSurname());
		to.setEmail(from.getEmail());*/
	}
	
	@Override
	public Medico getLoggedUser() throws Exception {
		//Obtener el usuario logeado
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		UserDetails loggedUser = null;

		//Verificar que ese objeto traido de sesion es el usuario
		if (principal instanceof UserDetails) {
			loggedUser = (UserDetails) principal;
		}
		
		Medico myUser = userRESTClient
				.getUserName(loggedUser.getUsername());
		
		return myUser;
	}

	@Override
	public Object getCitas(Medico medico) {

		CitasMedico citas = citaRESTClient.getCitas(medico);

		return citas.getListaCD();
	}

	@Override
	public Object getPredicciones(Medico user) {
		Predicciones predicciones = new Predicciones();
		
		predicciones = prediccionRESTClient.getPredicciones(user);
		
		return predicciones.getListaCD();
	}

	

	@Override
	public Medico loadUserByUsername(String username) {
		
		Medico medico = userRESTClient.getUserName(username);

		return medico;
	}


	@Override
	public Object getCitasAdministrativo() {
		CitasMedico citas = citaRESTClient.getCitasAdministrativo();
		
		return citas.getListaCD();
	}
}
