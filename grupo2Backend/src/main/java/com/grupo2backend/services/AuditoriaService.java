package com.grupo2backend.services;

import com.grupo2backend.dto.AuditoriaRankedDTO;
import com.grupo2backend.entity.AuditoriaEntity;
import com.grupo2backend.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Autowired
    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public List<AuditoriaEntity> getAuditoriasFromUserId(Long id) {
        return auditoriaRepository.findByUserId(id);
    }

    public List<AuditoriaRankedDTO> getMostOperationsUser() {
        return auditoriaRepository.getMostOperationsUsers();
    }
}
