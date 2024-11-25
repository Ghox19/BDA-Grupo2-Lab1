package com.grupo2backend.repository;

import com.grupo2backend.entity.CategoriaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class CategoriaRepository {

    @Autowired
    private Sql2o sql2o;

    public List<CategoriaEntity> findAll() {
        String sql = "SELECT id_categoria, nombre FROM categoria";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(CategoriaEntity.class);
        }
    }

    public void save(CategoriaEntity entity) {
        String sql = "INSERT INTO categoria (nombre) VALUES (:nombre)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nombre", entity.getNombre())
                    .executeUpdate();
        }
    }

    public CategoriaEntity findById(Long id) {
        String sql = "SELECT id_categoria, nombre FROM categoria WHERE id_categoria = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(CategoriaEntity.class);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM categoria WHERE id_categoria = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

    public void updateCategoria(Long id_categoria, String nombre) {
        final String updateQuery =
                "UPDATE categoria SET nombre = :nombre WHERE id_categoria = :id_categoria";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(updateQuery)
                    .addParameter("id_categoria", id_categoria)
                    .addParameter("nombre", nombre)
                    .executeUpdate();
            con.commit();
        }
    }
}
