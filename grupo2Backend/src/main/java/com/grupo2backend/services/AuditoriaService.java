package com.grupo2backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /* Ver exactamente como se van a ir tratando las cosas, ya sea con un insert, update o delete
    public void registrarCambio(Long idUsuario, String tipoOperacion, String descripcion) {
        String sql = "INSERT INTO parametros_trigger (id_usuario, tipo_operacion, descripcion) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, idUsuario, tipoOperacion, descripcion);
    }*/
}
