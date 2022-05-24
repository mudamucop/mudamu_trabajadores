package com.Mudamu.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Mudamu.model.CitasMedico;
import com.Mudamu.model.Medico;
import com.Mudamu.model.Prediccion;
import com.Mudamu.model.Predicciones;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Service
public class CitaRESTClient {
	// localhost -> Servidor IA

	String urlDDBBService = "http://mudamudb.duckdns.org/mudamuMysql/service/cita";

	ClientConfig clientConfig = new DefaultClientConfig();
	String response;
	int status;
	Client client;

	public CitaRESTClient() {
		client = Client.create(clientConfig);
	}

	public CitasMedico getCitas(Medico user) {
		CitasMedico citasMed = new CitasMedico();
		WebResource webResource = client.resource(urlDDBBService).path("citasMedico").queryParam("medicoID",
				Integer.toString(user.getTrabajadorID()));
		ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
		status = clientResponse.getStatus();
		if (status == 200) {
			citasMed = clientResponse.getEntity(CitasMedico.class);
		} else {
			response = "La llamada no ha sido correcta";
		}
		return citasMed;
	}

	public CitasMedico getCitasAdministrativo() {
		CitasMedico citasAdmin = new CitasMedico();
		WebResource webResource = client.resource(urlDDBBService).path("citasAdministrador");
		ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
		status = clientResponse.getStatus();
		if (status == 200) {
			citasAdmin = clientResponse.getEntity(CitasMedico.class);
		} else {
			response = "La llamada no ha sido correcta";
		}
		return citasAdmin;
	}
}
