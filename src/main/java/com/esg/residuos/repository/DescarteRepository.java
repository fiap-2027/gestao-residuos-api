package com.esg.residuos.repository;

import com.esg.residuos.domain.entity.Descarte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DescarteRepository extends JpaRepository<Descarte, Long> {
    List<Descarte> findByPontoColetaIdOrderByDataDescarteDesc(Long pontoColetaId);
    long countByDescarteCorreto(Boolean descarteCorreto);
}
