package com.grupo2backend.controller;

import com.grupo2backend.entity.OrdenEntity;
import com.grupo2backend.services.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orden")
public class OrdenController {

    @Autowired
    private OrdenService service;

    @GetMapping
    public List<OrdenEntity> getAll() {
        return service.getAllOrdenes();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody OrdenEntity entity) {
        return service.addOrden(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenEntity> getById(@PathVariable Long id) {
        OrdenEntity entity = service.getOrdenById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteOrden(id);
    }
}
