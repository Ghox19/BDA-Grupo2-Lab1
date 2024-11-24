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

    @GetMapping("/orden/{id}")
    public ResponseEntity<List<DetalleOrdenEntity>> getDetallesByOrdenId(@PathVariable Long id) {
        List<DetalleOrdenEntity> detalles = service.getDetallesByOrdenId(id);
        return detalles != null ? ResponseEntity.ok(detalles) : ResponseEntity.ok().body(null);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DetalleOrdenEntity entity) {
        return service.addDetalleOrden(entity);
    }

    @PutMapping()
    public ResponseEntity<DetalleOrdenEntity> update(@RequestBody DetalleOrdenEntity entity) {
        return service.updateDetalleOrden(entity) != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleOrdenEntity> getById(@PathVariable Long id) {
        DetalleOrdenEntity entity = service.getDetalleOrdenById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByProductoAndOrden/{idProducto}/{idOrden}")
    public ResponseEntity<DetalleOrdenEntity> findByProductoAndOrden(@PathVariable Long idProducto, @PathVariable Long idOrden) {
        DetalleOrdenEntity entity = service.findByProductoAndOrden(idProducto, idOrden);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteDetalleOrden(id);
    }
}
