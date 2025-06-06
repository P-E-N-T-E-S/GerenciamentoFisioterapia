package com.implantodontia.infraestrutura.persistencia.core.gestaoconsulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GestaoConsultaJPARepositorio extends JpaRepository<GestaoConsultaJPA, Long> {
    List<GestaoConsultaJPA> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    List<GestaoConsultaJPA> findByDataVencimentoBeforeAndClientePagouFalse(LocalDate dataVencimentoBefore);
}
