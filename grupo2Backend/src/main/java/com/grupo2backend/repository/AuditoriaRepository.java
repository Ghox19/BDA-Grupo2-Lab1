package com.grupo2backend.repository;

import com.grupo2backend.dto.AuditoriaRankedDTO;
import com.grupo2backend.entity.AuditoriaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class AuditoriaRepository {

    @Autowired
    private Sql2o sql2o;

    public List<AuditoriaRankedDTO> getMostOperationsUsers() {
        String sql = "SELECT * FROM get_user_most_operations()";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(AuditoriaRankedDTO.class);
        }
    }

    public List<AuditoriaEntity> findByUserId(Long id) {
        String sql = "SELECT id, id_cliente, id_objeto, nombre_tabla, operacion, fecha " +
                "FROM auditoria " +
                "WHERE id_cliente = :id " +
                "ORDER BY fecha DESC";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(AuditoriaEntity.class);
        }
    }
}
