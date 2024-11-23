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
            return new ResponseEntity<>("Se ingresó correctamente el detalle de la orden", HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo del error
            return new ResponseEntity<>("Error al ingresar el detalle de la orden: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<DetalleOrdenEntity> getDetallesByOrdenId(long idOrden) {
        return detalleOrdenRepository.findByOrdenId(idOrden);
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

    public DetalleOrdenEntity updateDetalleOrden(DetalleOrdenEntity detalleOrden) {
        DetalleOrdenEntity optionalDetalleOrden = detalleOrdenRepository.findById(detalleOrden.getId_detalle());
        System.out.println(detalleOrden.getId_detalle());
        if (optionalDetalleOrden != null){
            this.detalleOrdenRepository.deleteById(detalleOrden.getId_detalle());
            this.detalleOrdenRepository.save(detalleOrden);
            return detalleOrden;
        }
        return null;
    }

    public ResponseEntity<Object> deleteDetalleOrden(long id) {
        DetalleOrdenEntity optionalDetalleOrden = detalleOrdenRepository.findById(id);
        if (optionalDetalleOrden != null){
            this.detalleOrdenRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino correctamente el detalle de la orden", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
