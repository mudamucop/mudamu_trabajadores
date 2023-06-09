package com.Mudamu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.Mudamu.model.CitasMedico;
import com.Mudamu.model.Enfermedades;
import com.Mudamu.model.Medico;
import com.Mudamu.model.Predicciones;
import com.Mudamu.model.SintomasPredicciones;
import com.Mudamu.model.User;
import com.Mudamu.rest.CitaRESTClient;
import com.Mudamu.rest.PrediccionRESTClient;
import com.Mudamu.rest.SintoEnferRESTClient;
import com.Mudamu.rest.UserRESTClient;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRESTClient userRESTClient;

	@Autowired
	PrediccionRESTClient prediccionRESTClient;

	@Autowired
	CitaRESTClient citaRESTClient;

	@Autowired
	SintoEnferRESTClient sintEnferRESTClient;

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

	protected void mapUser(Medico from, Medico to) {
		to.setUsername(from.getUsername());
	}

	@Override
	public Medico getLoggedUser() throws Exception {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Medico myUser = userRESTClient
				.getUserName(((UserDetails) principal).getUsername());

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

	@Override
	public void updateCitaSolicitada(int prediccionID, int categoriaID) {
		prediccionRESTClient.updateCitaSolicitada(prediccionID, categoriaID);
	}

	@Override
	public Object getNuevasCitas() {
		CitasMedico citas = citaRESTClient.getNewCitasAdministrativo();

		return citas.getListaCD();
	}

	@Override
	public User loadByTarjetaUser(String tarjetaSanitaria) {
		User user = userRESTClient.getUser(tarjetaSanitaria);

		return user;
	}

	@Override
	public void crearCita(String predID, String fecha_hora, Integer pacienteID) {
		citaRESTClient.addNewCita(predID, fecha_hora, pacienteID);
		prediccionRESTClient.updateCitaDada(predID);
	}

	@Override
	public void avisoUpdate() {
		citaRESTClient.avisoCita();
	}

	@Override
	public Object getSintomas(String predId) {
		SintomasPredicciones sint = sintEnferRESTClient.getSintomas(predId);

		return sint;
	}

	@Override
	public Object getEnfermedades(String predId) {
		Enfermedades enfe = sintEnferRESTClient.getEnfermedades(predId);

		return enfe;
	}
}
