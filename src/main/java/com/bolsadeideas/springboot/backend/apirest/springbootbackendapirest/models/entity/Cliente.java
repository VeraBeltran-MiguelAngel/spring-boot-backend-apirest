package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/*
 * serializable por que al trabajar con formularios y spring se puede guardar dentro de los 
 * atributos de la sesión (o deseas serializarla)
 */
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // si fuera oracle eria sequence
    private Long id;
    //estas columnas coinciden con el nombre de las que estan en BD y se omite el @Column
    private String nombre;
    private String apellido;
    private String email;

    @Column(name = "create_at") // indica el nombre de la columna en nuestra BD
    @Temporal(TemporalType.DATE) // para transformar la fecha de java a la fecha date de SQL 
    private Date createAt;
    
    /**
     * Evento de ciclo de vida de las clases entity
     * Antes de persistir(guardar) asignamos la fecha
     */
    @PrePersist
    public void prePersit (){
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    // identificador estatico que se requiere cuando implementas el serializable
    private static final long serialVersionUID = 1L;
}
