package com.Mudamu.rest;

import org.springframework.stereotype.Service;

import com.Mudamu.model.Medico;
import com.Mudamu.model.Predicciones;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Service
public class PrediccionRESTClient {

	String urlDDBBService = "http://mudamudb.duckdns.org/mudamuMysql/service/pred";

	ClientConfig clientConfig = new DefaultClientConfig();
	String response;
	int status;
	Client client;

	public PrediccionRESTClient() {
		client = Client.create(clientConfig);
	}

	public Predicciones getPredicciones(Medico user) {
		Predicciones predicciones = new Predicciones();
		WebResource webResource = client.resource(urlDDBBService).path("predicciones").queryParam("medicoID",
				Integer.toString(user.getTrabajadorID()));
		ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
		status = clientResponse.getStatus();
		if (status == 200) {
			predicciones = clientResponse.getEntity(Predicciones.class);
		} else {
			response = "La llamada no ha sido correcta";
		}
		return predicciones;
	}

	public void updateCitaSolicitada(int prediccionID, int categoriaID) {
		WebResource webResource = client.resource(urlDDBBService).path("citaSolicitada").queryParam("prediccionID",
				Integer.toString(prediccionID)).queryParam("categoriaID", Integer.toString(categoriaID));
		ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
		status = clientResponse.getStatus();
		if (status != 204) {
			response = "La llamada no ha sido correcta";
		}
	}

	public void updateCitaDada(String predID) {
		WebResource webResource = client.resource(urlDDBBService).path("citaCreada").queryParam("prediccionID",
				predID);
		ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
		status = clientResponse.getStatus();
		if (status == 200) {
			response = "La llamada ha sido correcta";
		} else {
			response = "La llamada no ha sido correcta";
		}
	}
}
