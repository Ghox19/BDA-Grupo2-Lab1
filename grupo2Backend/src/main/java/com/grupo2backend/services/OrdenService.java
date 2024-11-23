package com.grupo2backend.services;

import com.grupo2backend.entity.OrdenEntity;
import com.grupo2backend.repository.OrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;

    @Autowired
    public OrdenService(OrdenRepository ordenRepository){this.ordenRepository = ordenRepository;};

    public List<OrdenEntity> getOrdenesByClienteId(Long idCliente) {
        return ordenRepository.findByClienteId(idCliente);
    }

    public ResponseEntity<Object> addOrden(OrdenEntity orden) {
        try {
            Long idOrden = ordenRepository.save(orden);
            System.out.println(idOrden);
            return new ResponseEntity<>(idOrden, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo del error
            return new ResponseEntity<>("Error al ingresar la orden: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<OrdenEntity> getAllOrdenes() {
        return ordenRepository.findAll();
    }

    public Optional<Integer> getOrdenProcesoByIdCliente(Long idCliente){
              return ordenRepository.findByEstadoAndIdCliente(idCliente);
    }

    public OrdenEntity getOrdenById(long id) {
        return ordenRepository.findById(id);
    }

    public ResponseEntity<Object> deleteOrden(long id) {
        OrdenEntity optionalOrden = ordenRepository.findById(id);
        if (optionalOrden != null){
            this.ordenRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino correctamente la Categoria", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public BigDecimal getTotalOrden(Long id){
        OrdenEntity orden = ordenRepository.findById(id);
        BigDecimal total = ordenRepository.getTotalOrden(id);

        if(orden != null) {
            orden.setTotal(total);
            return total;
        } else {
            return null;
        }
    }
}
