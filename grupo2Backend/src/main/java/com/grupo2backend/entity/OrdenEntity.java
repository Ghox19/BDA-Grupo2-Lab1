package com.grupo2backend.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrdenEntity {
    private Long id_orden;
    private Timestamp fecha_orden;
    private String estado;
    private Long id_cliente;
    private BigDecimal total;

    public Long getId_orden() {
        return id_orden;
    }

    public void setId_orden(Long id_orden) {
        this.id_orden = id_orden;
    }

    public Timestamp getFecha_orden() {
        return fecha_orden;
    }

    public void setFechaOrden(Timestamp fechaOrden) {
        this.fecha_orden = fechaOrden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
