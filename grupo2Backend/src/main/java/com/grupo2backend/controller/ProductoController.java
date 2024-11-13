package com.grupo2backend.controller;

import com.grupo2backend.entity.ProductoEntity;
import com.grupo2backend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public List<ProductoEntity> getAll() {
        return service.getAllProductos();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductoEntity entity) {
        return service.addProducto(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoEntity> getById(@PathVariable Long id) {
        ProductoEntity entity = service.getProductoById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteProducto(id);
    }
}
