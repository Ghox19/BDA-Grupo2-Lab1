package com.grupo2backend.services;

import com.grupo2backend.entity.CategoriaEntity;
import com.grupo2backend.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public ResponseEntity<Object> addCategoria(CategoriaEntity categoria) {
        try {
            categoriaRepository.save(categoria);
            return new ResponseEntity<>("Se ingresó correctamente la categoría", HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo del error
            return new ResponseEntity<>("Error al ingresar la categoría: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<CategoriaEntity> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public CategoriaEntity getCategoriaById(long id) {
        return categoriaRepository.findById(id);
    }

    public ResponseEntity<Object> deleteCategoria(long id) {
        CategoriaEntity optionalCategoria = categoriaRepository.findById(id);
        if (optionalCategoria != null){
            this.categoriaRepository.deleteById(id);
            return new ResponseEntity<>("Se elimino correctamente la Categoria", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public void updateCategoria(Long id_categoria, String nombre) {
        try {
            categoriaRepository.updateCategoria(id_categoria, nombre);
        } catch (Exception e) {
            throw new RuntimeException("Error updating categoria: " + e.getMessage());
        }
    }
}
