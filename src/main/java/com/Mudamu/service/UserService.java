package com.Mudamu.service;


import com.Mudamu.model.Medico;
import com.Mudamu.model.User;

public interface UserService {

	public Medico createUser(Medico user) throws Exception;

	public Medico getLoggedUser() throws Exception;

	public Object getCitas(Medico user);

	public Object getCitasAdministrativo();

    public Medico loadUserByUsername(String username);
	
	public User loadByTarjetaUser(String tarjetaSanitaria);

	public Object getPredicciones(Medico user);

    public void updateCitaSolicitada(int prediccionID, int categoriaID);

    public Object getNuevasCitas();

    public void crearCita(String predID, String fecha_hora, Integer pacienteID);

    public void avisoUpdate();

    public Object getSintomas(String predId);

    public Object getEnfermedades(String predId);
}

