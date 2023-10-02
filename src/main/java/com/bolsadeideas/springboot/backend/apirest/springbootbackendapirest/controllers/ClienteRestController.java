package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.services.IClienteService;

/**
 * ApiRest
 * A diferecnia de @Controller que solo es para apps web MVC con vistas
 * usaremos @RestController y crearemos la url o endpoint con @requestmapping
 * Debemos agregarel crossOrigin para que se comunique con angular(especificar
 * los dominios permitidos), si no especificas que metodos estan permitidos
 * la app considera que tiene todos los permisos
 */
@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

  /**
   * *Metodo index para listar los cientes.
   * Necesitamos ir al modelo a la clase service y obtener el listado de clientes
   * Para ello es necesaro inyectar el servicio(siempre la interfaz generica),
   * Recuerda que spring busca en automatico la clase que implementa dicha
   * interfaz
   * Si existiera mas de una implementacion de la interfaz debes usar un
   * calificador
   * Debemos mapear la url del metodo @Getmap
   * 
   * @return regresamos la lista de clientes
   */
  @Autowired
  private IClienteService clienteService;

  @GetMapping("/clientes")
  public List<Cliente> index() {
    return clienteService.findAll();
  }

  /**
   * *Mostrar por id
   * Tenemos que manejar los errores cuando ingresas un ID que no existe en la BD
   * Usamos la clase ResponseEntity para manejar mensajes de error y pasar el
   * objeto entity
   * a la respuesta responsebody
   * 
   * @param ?  debe ser generico, cuando ocurre un error puede ser de diferentes
   *           tipos
   * @param id del cliente por url por eso usa el @Pathvariable
   * @param e
   * @return el cliente con el id indicado
   */
  @GetMapping("/clientes/{id}")
  public ResponseEntity<?> show(@PathVariable Long id) {
    Cliente cliente = null;
    // para almacenar los mensajes de error
    Map<String, Object> response = new HashMap<>();

    /*
     * !Si llega a tener errores en el lado del servidor en la BD
     * !errores de conexion o sintaxis
     */
    try {
      // aqui buscamos el cliente
      cliente = clienteService.findById(id);
    } catch (DataAccessException e) {
      response.put("mensaje", "Error al realizar la consulta en la BD");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }
    // !Validar si el cliente es nulo
    if (cliente == null) {
      // colocamos el mensaje de error en el map
      response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la BD!")));
      // mostramos el mensaje al ser de tipo map hacemos un cast
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }

    /*
     * tiene su constructor el primer objeto =el contenido a guardar en el cuerpo
     * de la respuesta(response body)
     * 2° argumento el estado de la respuesta
     */
    return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
  }

  /**
   * *Crear cliente
   * Cambiamos el estatus default (200 ok) a un created 201
   * 
   * @param cliente contiene los datos para que se persistan(vienen en json desde
   *                angular)
   *                pero aqui se debe convertir en un objeto cliente, al venir en
   *                json dentro del cuerpo
   *                de la peticion del request debemos indicr el @RequestBody
   * @return el mensaje de creado o de error
   */
  @PostMapping("/clientes")
  public ResponseEntity<?> create(@RequestBody Cliente cliente) {
    /**
     * al crear un cliente debemos guardar la fecha de creacion podria quedar asi
     * cliente.setCreateAt(new Date()); java util pero no es elegante
     */
    Cliente clienteNew = null;
    Map<String, Object> response = new HashMap<>();
    try {
      // si no existe ningun error de insercion de datos guarda el registro
      clienteNew = clienteService.save(cliente);
    }
    // ! si existe problema al guardar el cliente (validaciones de tipo de dato
    // ingresado)
    catch (DataAccessException e) {
      response.put("mensaje", "Error al realizar el insert en la BD");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    response.put("mensaje", "El cliente ha sio creado con exito!");
    response.put("cliente", clienteNew);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
  }

  /**
   * *Actualizar cliente
   * 
   * @param cliente,id
   *                   el 1°cliente que esta dentro del cuerpo del request y un id
   */
  @PutMapping("clientes/{id}")
  public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
    // primero obtenemos el cliente de la BD con el id que nos proporcionan
    Cliente clienteActual = clienteService.findById(id);
    Cliente clienteUpdated = null;
    Map<String, Object> response = new HashMap<>();

    // !Validar si el cliente es nulo
    if (clienteActual == null) {
      // colocamos el mensaje de error en el map
      response.put("mensaje",
          "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existe en la BD!")));
      // mostramos el mensaje al ser de tipo map hacemos un cast
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
    }
    try {
      // actualizamos los datos del cliente encontrado por el id
      clienteActual.setApellido(cliente.getApellido());
      clienteActual.setNombre(cliente.getNombre());
      clienteActual.setEmail(cliente.getEmail());
      clienteActual.setCreateAt(cliente.getCreateAt());
      /*
       * el metodo save funciona para updates e insert(cuando el id es null),
       * cuando el id tiene un valor hara un merge (update).
       * Guardamos el cliente actualizado
       */
      clienteUpdated = clienteService.save(clienteActual);
    }
    // ! si existe problema al actualizar el cliente (validaciones de tipo de dato
    // ingresado)
    catch (DataAccessException e) {
      response.put("mensaje", "Error al actualizar el cliente en la BD");
      response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
      return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    response.put("mensaje", "El cliente ha sio actualizado con exito!");
    response.put("cliente", clienteUpdated);
    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

  }

  /**
   * *Eliminar cliente
   */
  @DeleteMapping("clientes/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    clienteService.delete(id);
  }

}
