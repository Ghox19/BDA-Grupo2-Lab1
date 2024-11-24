package com.grupo2backend.services;

import com.grupo2backend.entity.ProductoEntity;
import com.grupo2backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {this.productoRepository = productoRepository;};

    public ResponseEntity<Object> addProducto(ProductoEntity producto) {
        try {
            productoRepository.save(producto);
            return new ResponseEntity<>("Se ingresó correctamente la categoría", HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo del error
            return new ResponseEntity<>("Error al ingresar la categoría: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ProductoEntity> getAllProductos(Integer page, Integer size) {
        return productoRepository.findAll(page, size);
    }

    public ProductoEntity getProductoById(long id) {
        return productoRepository.findById(id);
    }

    public Long getCount(){return productoRepository.countAll();};

    public ResponseEntity<Object> deleteProducto(long id) {
        ProductoEntity optionalProducto = productoRepository.findById(id);
        if (optionalProducto != null){
            this.productoRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino correctamente la Categoria", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
