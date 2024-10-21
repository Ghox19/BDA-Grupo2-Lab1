package com.grupo2backend.controller;

import com.grupo2backend.entity.ClienteEntity;
import com.grupo2backend.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public List<ClienteEntity> getAll() {
        return service.getAllClientes();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ClienteEntity entity) {
        return service.addCliente(entity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteEntity> getById(@PathVariable Long id) {
        ClienteEntity entity = service.getClienteById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return service.deleteCliente(id);
    }
}
