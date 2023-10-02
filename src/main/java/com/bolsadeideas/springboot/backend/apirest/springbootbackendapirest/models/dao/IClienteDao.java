package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
/**
 * CRUD repository ya tiene metodos preconfigurdos para operaciones CRUD
 */
public interface IClienteDao extends CrudRepository<Cliente,Long>{


    
}
