package com.grupo2backend.dto;

public class RankedDTO {
    private String nombre;
    private String categoria;
    private Long id_producto;
    private Integer stock;
    private Integer total_vendido;
    private Double ingreso_total;
    private Double porcentaje_ventas;

    // Constructor
    public RankedDTO(String nombre, String categoria, Long idProducto,
                            Integer stock, Integer totalVendido, Double ingresoTotal,
                            Double porcentajeVentas) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.id_producto = idProducto;
        this.stock = stock;
        this.total_vendido = totalVendido;
        this.ingreso_total = ingresoTotal;
        this.porcentaje_ventas = porcentajeVentas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTotal_vendido() {
        return total_vendido;
    }

    public void setTotal_vendido(Integer total_vendido) {
        this.total_vendido = total_vendido;
    }

    public Double getIngreso_total() {
        return ingreso_total;
    }

    public void setIngreso_total(Double ingreso_total) {
        this.ingreso_total = ingreso_total;
    }

    public Double getPorcentaje_ventas() {
        return porcentaje_ventas;
    }

    public void setPorcentaje_ventas(Double porcentaje_ventas) {
        this.porcentaje_ventas = porcentaje_ventas;
    }
}
