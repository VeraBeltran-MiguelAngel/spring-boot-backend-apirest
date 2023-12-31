package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.dao.IClienteDao;
import com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.entity.Cliente;

/**
 * Clase de tipo componente de servicio se gusrda en el contenedor de spring queda
 * almacenado en el contexto y despes puedes inyectar este objeto(bean) en el 
 * controlador
 */
@Service
public class ClientesServiceImpl implements IClienteService {

    // tenemos que inyectar el cienteDao
    @Autowired
    private IClienteDao clienteDao;

    @Override
    //para manejar transaccion en el metodo(como es un select) se coloca read only 
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        // para accesar a los clientes , este metodo regresa un iterable y se debe hacer
        // un cast a lista clientes
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true) //transacional de lectura
    public Cliente findById(Long id) {
        // si encuentra el id lo devuelve caso contrario envia null
        return clienteDao.findById(id).orElse(null);
       
    }

    @Override
    @Transactional //transaccional completo
    public Cliente save(Cliente cliente) {
        // guarda la entidad
        return clienteDao.save(cliente);
        
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // elimina por id
        clienteDao.deleteById(id);
        
    }

}
