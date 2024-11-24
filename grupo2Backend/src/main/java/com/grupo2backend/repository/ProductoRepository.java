package com.grupo2backend.repository;

import com.grupo2backend.entity.ProductoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ProductoRepository {

    @Autowired
    private Sql2o sql2o;

    public List<ProductoEntity> findAll(int page, int size) {
        String sql = "SELECT id_producto, nombre, descripcion, precio, stock, estado, id_categoria " +
                "FROM producto " +
                "ORDER BY id_producto " +
                "LIMIT :limit OFFSET :offset";

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("limit", size)
                    .addParameter("offset", (page - 1) * size)
                    .executeAndFetch(ProductoEntity.class);
        }
    }

    public void save(ProductoEntity entity) {
        String sql = "INSERT INTO producto (nombre, descripcion, precio, stock, estado, id_categoria) VALUES (:nombre, :descripcion, :precio, :stock, :estado, :id_categoria)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nombre", entity.getNombre())
                    .addParameter("descripcion", entity.getDescripcion())
                    .addParameter("precio", entity.getPrecio())
                    .addParameter("stock", entity.getStock())
                    .addParameter("estado", entity.getEstado())
                    .addParameter("id_categoria", entity.getId_categoria())
                    .executeUpdate();
        }
    }

    public ProductoEntity findById(Long id) {
        String sql = "SELECT id_producto, nombre, descripcion, precio, stock, estado, id_categoria FROM producto WHERE id_producto = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(ProductoEntity.class);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM producto WHERE id_producto = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public long countAll() {
        String sql = "SELECT COUNT(*) FROM producto";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeScalar(Long.class);
        }
    }
}
