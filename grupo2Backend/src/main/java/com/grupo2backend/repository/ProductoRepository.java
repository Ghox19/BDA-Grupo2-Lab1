package com.grupo2backend.repository;

import com.grupo2backend.dto.RankedDTO;
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

    public void updateProducto(Long id, ProductoEntity producto) {
        final String updateQuery =
                "UPDATE producto SET nombre = :nombre, descripcion = :descripcion, precio = :precio, stock = :stock, estado = :estado, id_categoria = :id_categoria WHERE id_producto = :id";

        try (Connection con = sql2o.beginTransaction()) {
            con.createQuery(updateQuery)
                    .addParameter("id", id)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("descripcion", producto.getDescripcion())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("estado", producto.getEstado())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .executeUpdate();
            con.commit();
        }
    }

    public List<RankedDTO> obtenerDetalleVentasPorCategoria() {
        String sql = """
            WITH detalle_productos AS (
                SELECT
                    p.id_producto,
                    p.nombre AS nombre,
                    c.nombre AS categoria,
                    p.stock,
                    SUM(d_o.cantidad) AS total_vendido,
                    SUM(d_o.cantidad * d_o.precio_unitario) AS ingreso_total
                FROM
                    detalle_orden d_o
                JOIN
                    producto p ON d_o.id_producto = p.id_producto
                JOIN
                    categoria c ON p.id_categoria = c.id_categoria
                GROUP BY
                    p.id_producto, p.nombre, c.nombre, p.stock
            ),
            totales AS (
                SELECT
                    SUM(ingreso_total) AS total_ingresos
                FROM
                    detalle_productos
            )
            SELECT DISTINCT ON(dp.categoria)
                dp.nombre,
                dp.categoria,
                dp.id_producto,
                dp.stock,
                dp.total_vendido,
                dp.ingreso_total,
                ROUND(100.0 * (dp.ingreso_total / t.total_ingresos), 2) AS porcentaje_ventas
            FROM
                detalle_productos dp
            CROSS JOIN
                totales t
            ORDER BY
                dp.categoria,  
                dp.ingreso_total DESC, 
                dp.stock DESC
        """;

        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(RankedDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el detalle de ventas por categoría", e);
        }
    }
}
