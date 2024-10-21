package com.grupo2backend.services;

import com.grupo2backend.entity.CategoriaEntity;
import com.grupo2backend.entity.ClienteEntity;
import com.grupo2backend.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public ResponseEntity<Object> addCliente(ClienteEntity cliente) {
        try {
            clienteRepository.save(cliente);
            return new ResponseEntity<>("Se ingresó correctamente la categoría", HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo del error
            return new ResponseEntity<>("Error al ingresar la categoría: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<ClienteEntity> getAllClientes() {
        return clienteRepository.findAll();
    }

    public ClienteEntity getClienteById(long id) {
        return clienteRepository.findById(id);
    }

    public ResponseEntity<Object> deleteCliente(long id) {
        ClienteEntity optionalCliente = clienteRepository.findById(id);
        if (optionalCliente != null){
            this.clienteRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino correctamente la Categoria", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}