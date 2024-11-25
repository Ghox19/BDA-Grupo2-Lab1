package com.grupo2backend.controller;


import com.grupo2backend.dto.ProductoDTO;
import com.grupo2backend.dto.RankedDTO;
import com.grupo2backend.entity.ClienteEntity;
import com.grupo2backend.entity.ProductoEntity;
import com.grupo2backend.services.ClienteService;
import com.grupo2backend.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class publicController {
    @Autowired
    private ProductoService service;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/prod")
    public ProductoDTO findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<ProductoEntity> productos = service.getAllProductos(page, size);
        long total = service.getCount();

        return new ProductoDTO(productos, page, size, total);
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

    @GetMapping("/prod/top")
    public List<RankedDTO> getTopProduct(){
        return service.getTopProduct();
    }
}
