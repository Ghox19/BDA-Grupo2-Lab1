package com.grupo2backend.controller;

import ch.qos.logback.core.net.server.Client;
import com.grupo2backend.entity.ClienteEntity;
import com.grupo2backend.entity.ProductoEntity;
import com.grupo2backend.services.ClienteService;
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

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/prod")
    public List<ProductoEntity> getAll() {
        return service.getAllProductos();
    }

    @GetMapping("/prod/{id}")
    public ResponseEntity<ProductoEntity> getById(@PathVariable Long id) {
        ProductoEntity entity = service.getProductoById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<ClienteEntity> getByIdClient(@PathVariable Long id) {
        ClienteEntity entity = clienteService.getClienteById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }
}
