package com.grupo2backend.repository;

import com.grupo2backend.entity.OrdenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class OrdenRepository {

    @Autowired
    private Sql2o sql2o;

    public List<OrdenEntity> findAll() {
        String sql = "SELECT id_orden, fecha_orden, estado, id_cliente, total FROM orden";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(OrdenEntity.class);
        }
    }

    public void save(OrdenEntity entity) {
        String sql = "INSERT INTO orden (fecha_orden, estado, id_cliente, total) VALUES (:fecha_orden, :estado, :id_cliente, :total)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("fecha_orden", entity.getFecha_orden())
                    .addParameter("estado", entity.getEstado())
                    .addParameter("id_cliente", entity.getId_cliente())
                    .addParameter("total", entity.getTotal())
                    .executeUpdate();
        }
    }

    public OrdenEntity findById(Long id) {
        String sql = "SELECT id_orden, fecha_orden, estado, id_cliente, total FROM orden WHERE id_orden = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(OrdenEntity.class);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM orden WHERE id_orden = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }


    public BigDecimal getTotalOrden(Long id) {
        String sql = "SELECT calcular_total_orden(:id)";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeScalar(BigDecimal.class);
        }
    }
}
