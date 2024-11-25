package com.grupo2backend.controller;

import com.grupo2backend.entity.OrdenEntity;
import com.grupo2backend.services.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/cliente/{idCliente}")
    public List<OrdenEntity> getOrdenesByClienteId(@PathVariable Long idCliente) {
        return service.getOrdenesByClienteId(idCliente);
    }
    @GetMapping("/ordencliente/{id_cliente}")
    public Optional<Integer> getOrdenById(@PathVariable Long id_cliente){
        return service.getOrdenProcesoByIdCliente(id_cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteOrden(id);
    }

    @PutMapping("/calcularTotalOrden/{id}")
    public ResponseEntity<Object> getTotalOrden(@PathVariable Long id) {
        OrdenEntity orden = service.getOrdenById(id);

        if (orden == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Orden no encontrada");
        }

        BigDecimal totalOrden = service.getTotalOrden(id);

        return ResponseEntity.ok(totalOrden);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrden(
            @PathVariable("id") Long id,
            @RequestBody OrdenEntity orden) {
        try {
            service.updateOrden(id, orden);
            return ResponseEntity.ok("Orden updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating orden: " + e.getMessage());
        }
    }
}
