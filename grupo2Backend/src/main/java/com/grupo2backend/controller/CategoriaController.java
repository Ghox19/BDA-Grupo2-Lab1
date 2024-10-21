package com.grupo2backend.controller;

import com.grupo2backend.entity.CategoriaEntity;
import com.grupo2backend.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<CategoriaEntity> getAll() {
        return service.getAllCategorias();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CategoriaEntity entity) {
        return service.addCategoria(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEntity> getById(@PathVariable Long id) {
        CategoriaEntity entity = service.getCategoriaById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteCategoria(id);
    }
}