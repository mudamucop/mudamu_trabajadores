package com.Mudamu.rest;

import org.springframework.stereotype.Service;

import com.Mudamu.model.Enfermedades;
import com.Mudamu.model.SintomasPredicciones;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Service
public class SintoEnferRESTClient {
    String urlDDBBService = "http://mudamudb.duckdns.org/mudamuMysql/service/enfermedades_sintomas";

    ClientConfig clientConfig = new DefaultClientConfig();
    String response;
    int status;
    Client client;

    public SintoEnferRESTClient() {
        client = Client.create(clientConfig);
    }

    public SintomasPredicciones getSintomas(String predId) {
        SintomasPredicciones user = new SintomasPredicciones();
        WebResource webResource = client.resource(urlDDBBService).path("sintomasPaciente").queryParam("predID",
                predId);
        ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
        status = clientResponse.getStatus();
        if (status == 200) {
            user = clientResponse.getEntity(SintomasPredicciones.class);
        } else {
            response = "La llamada no ha sido correcta";
        }
        return user;
    }

    public Enfermedades getEnfermedades(String predId) {
        Enfermedades user = new Enfermedades();
        WebResource webResource = client.resource(urlDDBBService).path("enfermedades").queryParam("predID",
                predId);
        ClientResponse clientResponse = webResource.accept("application/xml").get(ClientResponse.class);
        status = clientResponse.getStatus();
        if (status == 200) {
            user = clientResponse.getEntity(Enfermedades.class);
        } else {
            response = "La llamada no ha sido correcta";
        }
        return user;
    }
}