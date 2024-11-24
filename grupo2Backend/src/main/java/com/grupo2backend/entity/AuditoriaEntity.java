package com.grupo2backend.entity;

//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;

import java.sql.Timestamp;


public class AuditoriaEntity {
    private Long id;
    private Long id_cliente;
    private Long id_objeto;
    private String nombre_tabla;
    private String operacion;
    private Timestamp fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Long getId_objeto() {
        return id_objeto;
    }

    public void setId_objeto(Long id_objeto) {
        this.id_objeto = id_objeto;
    }

    public String getNombre_tabla() {
        return nombre_tabla;
    }

    public void setNombre_tabla(String nombre_tabla) {
        this.nombre_tabla = nombre_tabla;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
