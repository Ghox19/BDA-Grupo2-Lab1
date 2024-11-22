package com.grupo2backend.services;

import com.grupo2backend.entity.DetalleOrdenEntity;
import com.grupo2backend.repository.DetalleOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleOrdenService {

    private final DetalleOrdenRepository detalleOrdenRepository;

    @Autowired
    public DetalleOrdenService(DetalleOrdenRepository detalleOrdenRepository){this.detalleOrdenRepository = detalleOrdenRepository;};

    public ResponseEntity<Object> addDetalleOrden(DetalleOrdenEntity detalleOrden) {
        try {
            detalleOrdenRepository.save(detalleOrden);
            return new ResponseEntity<>("Se ingresó correctamente la categoría", HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo del error
            return new ResponseEntity<>("Error al ingresar la categoría: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<DetalleOrdenEntity> getAllDetalleOrden() {
        return detalleOrdenRepository.findAll();
    }

    public DetalleOrdenEntity getDetalleOrdenById(long id) {
        return detalleOrdenRepository.findById(id);
    }

    public DetalleOrdenEntity findByProductoAndOrden(long idProducto, long idOrden) {
        return detalleOrdenRepository.findByProductoAndOrden(idProducto, idOrden);
    }

    public ResponseEntity<Object> deleteDetalleOrden(long id) {
        DetalleOrdenEntity optionalDetalleOrden = detalleOrdenRepository.findById(id);
        if (optionalDetalleOrden != null){
            this.detalleOrdenRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino correctamente la Categoria", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
