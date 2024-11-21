package com.grupo2backend.controller;

import com.grupo2backend.entity.ProductoEntity;
import com.grupo2backend.services.OrdenService;
import com.grupo2backend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public")
public class publicController {
    @Autowired
    private ProductoService service;

    @GetMapping("/prod")
    public List<ProductoEntity> getAll() {
        return service.getAllProductos();
    }

    @GetMapping("/prod/{id}")
    public ResponseEntity<ProductoEntity> getById(@PathVariable Long id) {
        ProductoEntity entity = service.getProductoById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

}
