package com.grupo2backend.controller;

import com.grupo2backend.dto.AuditoriaRankedDTO;
import com.grupo2backend.entity.AuditoriaEntity;
import com.grupo2backend.services.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    @Autowired
    AuditoriaService service;

    @GetMapping
    public List<AuditoriaRankedDTO> getMostOperationsUser() {
        return service.getMostOperationsUser();
    }

    @GetMapping("/{id}")
    public List<AuditoriaEntity> getAuditoriasByUserId(@PathVariable Long id){return service.getAuditoriasFromUserId(id);}
}
