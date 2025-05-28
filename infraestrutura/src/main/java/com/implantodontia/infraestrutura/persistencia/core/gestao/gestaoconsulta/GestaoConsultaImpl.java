package com.implantodontia.infraestrutura.persistencia.core.gestao.gestaoconsulta;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaRepository;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class GestaoConsultaImpl implements ConsultaRepository {

    private final GestaoConsultaJPARepositorio gestaoConsultaRepositorio;
    private final JpaMapeador mapeador;

    public GestaoConsultaImpl(GestaoConsultaJPARepositorio gestaoConsultaRepositorio, JpaMapeador mapeador) {
        this.gestaoConsultaRepositorio = gestaoConsultaRepositorio;
        this.mapeador = mapeador;
    }

    @Override
    public void salvar(Consulta consulta){
        GestaoConsultaJPA consultaJPA = mapeador.map(consulta, GestaoConsultaJPA.class);
        gestaoConsultaRepositorio.save(consultaJPA);
    }

    @Override
    public List<Consulta> buscarPorData(LocalDate data){
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.plusDays(1).atStartOfDay().minusNanos(1);

        List<GestaoConsultaJPA> consultas = gestaoConsultaRepositorio.findByDataHoraBetween(inicio, fim);

        return consultas.stream().map(consulta -> mapeador.map(consulta, Consulta.class)).collect(Collectors.toList());
    }
    @Override
    public List<Consulta> listarConsultas(){
        List<GestaoConsultaJPA> consultas = gestaoConsultaRepositorio.findAll();
        return consultas.stream().map(c->mapeador.map(c, Consulta.class)).toList();

    }

    @Override
    public Consulta buscarPorId(Long id){
        return gestaoConsultaRepositorio.findById(id).map(c->mapeador.map(c, Consulta.class)).orElse(null);
    }


}
