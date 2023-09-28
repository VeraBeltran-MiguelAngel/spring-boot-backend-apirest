package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    /**
     * Mostrar por id
     * @param id del cliente por url por eso usa el @Pathvariable
     * @return el cliente con el id indicado
     */
    @GetMapping("/clientes/{id}")
    public Cliente show (@PathVariable Long id){
      return clienteService.findById(id);
    }

    /**
     * Crear cliente
     * Cambiamos el estatus default (200 ok) a un created 201
     * @param cliente contiene los datos para que se persistan(vienen en json desde angular)
     * pero aqui se debe convertir en un objeto cliente, al venir en json dentro del cuerpo
     * de la peticion del request debemos indicr el @RequestBody
     * @return el cliente creado
     */
    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente create(@RequestBody Cliente cliente){
      /**
       * al crear un cliente debemos guardar la fecha de creacion podria quedar asi
       * cliente.setCreateAt(new Date()); java util pero no es elegante
       */
      return clienteService.save(cliente);
    }
}

