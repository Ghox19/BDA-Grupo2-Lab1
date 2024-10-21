package com.grupo2backend.controller;

import com.grupo2backend.entity.DetalleOrdenEntity;
import com.grupo2backend.services.DetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalleOrden")
public class DetalleOrdenController {

    @Autowired
    private DetalleOrdenService service;

    @GetMapping
    public List<DetalleOrdenEntity> getAll() {
        return service.getAllDetalleOrden();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DetalleOrdenEntity entity) {
        return service.addDetalleOrden(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrdenEntity> getById(@PathVariable Long id) {
        DetalleOrdenEntity entity = service.getDetalleOrdenById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteDetalleOrden(id);
    }
}
