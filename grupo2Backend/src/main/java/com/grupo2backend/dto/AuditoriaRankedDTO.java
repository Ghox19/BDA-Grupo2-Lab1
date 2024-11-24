package com.grupo2backend.dto;

public class AuditoriaRankedDTO {
    private Long id_usuario;
    private String tipo_operacion;
    private Integer total_operaciones;

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public Integer getTotal_operaciones() {
        return total_operaciones;
    }

    public void setTotal_operaciones(Integer total_operaciones) {
        this.total_operaciones = total_operaciones;
    }
}
