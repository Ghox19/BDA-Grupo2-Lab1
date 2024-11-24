package com.grupo2backend.dto;

import com.grupo2backend.entity.ProductoEntity;

import java.util.List;

public class ProductoDTO {
    private List<ProductoEntity> productos;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public ProductoDTO(List<ProductoEntity> productos, int currentPage,
                                int pageSize, long totalElements) {
        this.productos = productos;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }

    public List<ProductoEntity> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoEntity> productos) {
        this.productos = productos;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
