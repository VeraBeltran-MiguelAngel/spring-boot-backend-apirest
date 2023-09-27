package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.services.IClienteService;

/**
 * ApiRest
 * A diferecnia de @Controller que solo es para apps web MVC con vistas
 * usaremos @RestController  y crearemos la url o endpoint con @requestmapping 
 * Debemos agregarel crossOrigin para que se comunique con angular(especificar 
 * los dominios permitidos), si no especificas que metodos estan permitidos
 * la app considera que tiene todos los permisos
 */
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

   /**
    * metodo index para listar los cientes.
    * Necesitamos ir al modelo a la clase service y obtener el listado de clientes
    * Para ello es necesaro inyectar el servicio(siempre la interfaz generica), 
    * Recuerda que spring busca en automatico la clase que implementa dicha interfaz
    * Si existiera mas de una implementacion de la interfaz debes usar un calificador
    * Debemos mapear la url del metodo @Getmap
    * @return regresamos la lista de clientes
    */
    @Autowired
    private IClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> index(){
       return clienteService.findAll();
    }
}
