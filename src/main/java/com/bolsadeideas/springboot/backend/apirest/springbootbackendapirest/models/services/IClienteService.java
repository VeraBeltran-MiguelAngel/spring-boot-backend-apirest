package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.services;

import java.util.List;

import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;

public interface IClienteService {

    //colocamos los metodos que debera tener el CRUD
    public List<Cliente> findAll();
    public Cliente findById(Long id);
    public Cliente save(Cliente cliente);
    public void delete(Long id);




}
