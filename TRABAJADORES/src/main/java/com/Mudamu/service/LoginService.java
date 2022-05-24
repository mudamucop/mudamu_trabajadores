package com.Mudamu.service;

import java.util.Optional;

import com.Mudamu.model.Medico;

public interface LoginService {

	//public Iterable<User> getAllUsers();

	public Medico createUser(Medico user) throws Exception;

	public Medico getLoggedUser() throws Exception;

	public Object getCitas(Medico user);

	public Object getCitasAdministrativo();

    public Medico loadUserByUsername(String username);

	public Object getPredicciones(Medico user);
}

