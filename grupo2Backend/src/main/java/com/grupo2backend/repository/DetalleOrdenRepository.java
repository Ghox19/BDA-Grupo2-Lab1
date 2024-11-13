package com.grupo2backend.repository;

import com.grupo2backend.entity.DetalleOrdenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class DetalleOrdenRepository {

    @Autowired
    private Sql2o sql2o;

    public List<DetalleOrdenEntity> findAll() {
        String sql = "SELECT id_detalle, id_orden, id_producto, cantidad, precio_unitario FROM detalle_orden";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(DetalleOrdenEntity.class);
        }
    }

    public void save(DetalleOrdenEntity entity) {
        String sql = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES (:id_orden, :id_producto, :cantidad, :precio_unitario)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id_orden", entity.getId_orden())
                    .addParameter("id_producto", entity.getId_producto())
                    .addParameter("cantidad", entity.getCantidad())
                    .addParameter("precio_unitario", entity.getPrecio_unitario())
                    .executeUpdate();
        }
    }

    public DetalleOrdenEntity findById(Long id) {
        String sql = "SELECT id_detalle, id_orden, id_producto, cantidad, precio_unitario FROM detalle_orden WHERE id_detalle = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(DetalleOrdenEntity.class);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM detalle_orden WHERE id_detalle = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
