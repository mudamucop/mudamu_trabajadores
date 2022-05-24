package com.Mudamu.rest;

import javax.ws.rs.core.MultivaluedMap;

import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.Mudamu.model.Medico;
import com.Mudamu.model.Predicciones;

@Service
public class UserRESTClient {
	//localhost/Servidor ->http://localhost:8080/AlbumDbCRUD/resources/user		
	
	String urlDDBBService = "http://mudamudb.duckdns.org/mudamuMysql/service/user";
	//String urlDDBBService = "http://localhost/MudamuCrud/service/user";
	
	ClientConfig clientConfig = new DefaultClientConfig();
	String response;  
	int status;
	Client client;
	
	public UserRESTClient() {
		client= Client.create(clientConfig);
	}
	
	public Medico getUserName(String username) {
		
		Medico user= new Medico();
		WebResource webResource = client.resource(urlDDBBService).path("medicoLogin").queryParam("username",username);
		ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
		status= clientResponse.getStatus();
		if (status==200) {
			user = clientResponse.getEntity(Medico.class);
			}
		else {response="La llamada no ha sido correcta";}
		return user;
	}

	//Delete User con path params	
	public String userDelete(int id) {
			WebResource webResource = client.resource(urlDDBBService).path("deleteMedico").path(Integer.toString(id));
			ClientResponse clientResponse = webResource.type("text/plain").delete(ClientResponse.class);
			status= clientResponse.getStatus();
			if (status==204) {response = "User Borrado";}
			else {response="NO encontrado";}
			return response;
		}

	public Medico login(String username, String password) {
		
		Medico user=new Medico();
		WebResource webResource = client.resource(urlDDBBService).path("medicoLogin").queryParam("username", username).queryParam("password", password);
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("username", username);
		queryParams.add("password", password);
	
		ClientResponse clientResponse = webResource.queryParams(queryParams).accept("application/xml").get(ClientResponse.class);
		
		status= clientResponse.getStatus();
		if (status==200) {
			user = clientResponse.getEntity(Medico.class);
			}
		else {response="La llamada no ha sido correcta";}

		return user;
	}

	public String postUserXml(Medico user) {
		WebResource webResource = client.resource(urlDDBBService).path("registerMedico");
		ClientResponse clientResponse = webResource.type("application/xml").post(ClientResponse.class, user);
		status= clientResponse.getStatus();
		if (status==201) {response = "Creado con Objeto";}
		else {response="La llamada no ha sido correcta";}
		return response;
	}	
}


