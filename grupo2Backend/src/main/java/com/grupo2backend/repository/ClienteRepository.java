package com.grupo2backend.repository;

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
        String sql = "SELECT id_cliente, nombre, direccion, email, telefono, clave FROM cliente";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(ClienteEntity.class);
        }
    }

    public void save(ClienteEntity entity) {
        String sql = "INSERT INTO cliente (nombre, direccion, email, telefono, clave) VALUES (:nombre, :direccion, :email, :telefono, :clave)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("nombre", entity.getNombre())
                    .addParameter("direccion", entity.getDireccion())
                    .addParameter("email", entity.getEmail())
                    .addParameter("telefono", entity.getTelefono())
                    .addParameter("clave", entity.getClave())
                    .executeUpdate();
        }
    }

    public ClienteEntity findById(Long id) {
        String sql = "SELECT id_cliente, nombre, direccion, email, telefono, clave FROM cliente WHERE id_cliente = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(ClienteEntity.class);
        }
    }

    public ClienteEntity findByEmail(String Email){
        String sql = "SELECT id_cliente, nombre, direccion, email, telefono, clave FROM cliente WHERE email = :Email";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("Email", Email)
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

    public String actualizarEstadoOrden(Integer id) {
        String sql = "SELECT actualizar_estado_orden(:id)";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeScalar(String.class);
        }
    }

    public void updateCliente(Long id, ClienteEntity cliente) {
        final String updateQuery =
                "UPDATE cliente SET nombre = :nombre, direccion = :direccion, email = :email, telefono = :telefono, clave = :clave WHERE id_cliente = :id_cliente";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(updateQuery)
                    .addParameter("id_cliente", id)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("clave", cliente.getClave())
                    .executeUpdate();
            con.commit();
        }
    }
}
