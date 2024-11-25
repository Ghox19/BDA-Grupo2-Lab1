package com.grupo2backend.controller;

import com.grupo2backend.entity.ProductoEntity;
import com.grupo2backend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProductoEntity entity) {
        return service.addProducto(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteProducto(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProducto(
            @PathVariable("id") Long id,
            @RequestBody ProductoEntity producto) {
        try {
            service.updateProducto(id, producto);
            return ResponseEntity.ok("Producto updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating producto: " + e.getMessage());
        }
    }
}
