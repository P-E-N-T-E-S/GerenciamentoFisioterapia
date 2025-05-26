package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaoconsulta;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GestaoConsultaJPARepositorio extends JpaRepository<GestaoConsultaJPA, Long> {
    List<GestaoConsultaJPA> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
}
