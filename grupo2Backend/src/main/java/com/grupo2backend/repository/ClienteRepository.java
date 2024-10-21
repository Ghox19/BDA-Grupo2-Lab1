package com.grupo2backend.repository;

import com.grupo2backend.entity.CategoriaEntity;
import com.grupo2backend.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private Sql2o sql2o;

    public List<ClienteEntity> findAll() {
        String sql = "SELECT id_cliente, nombre, direccion, email, telefono FROM cliente";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(ClienteEntity.class);
        }
    }

    public void save(ClienteEntity entity) {
        String sql = "INSERT INTO cliente (nombre, direccion, email, telefono) VALUES (:nombre, :direccion, :email, :telefono)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nombre", entity.getNombre())
                    .addParameter("direccion", entity.getDireccion())
                    .addParameter("email", entity.getEmail())
                    .addParameter("telefono", entity.getTelefono())
                    .executeUpdate();
        }
    }

    public ClienteEntity findById(Long id) {
        String sql = "SELECT id_cliente, nombre, direccion, email, telefono FROM cliente WHERE id_cliente = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(ClienteEntity.class);
        }
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
