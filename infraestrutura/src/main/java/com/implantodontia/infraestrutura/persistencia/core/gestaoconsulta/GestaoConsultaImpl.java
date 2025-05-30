package com.implantodontia.infraestrutura.persistencia.core.gestaoconsulta;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaRepository;
import com.implantodontia.infraestrutura.persistencia.JpaMapeador;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPA;
import com.implantodontia.infraestrutura.persistencia.core.gestaopaciente.endereco.EnderecoJPARepositorio;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GestaoConsultaImpl implements ConsultaRepository {

    private final GestaoConsultaJPARepositorio gestaoConsultaRepositorio;
    private final JpaMapeador mapeador;
    private EnderecoJPARepositorio enderecoRepositorio;

    public GestaoConsultaImpl(GestaoConsultaJPARepositorio gestaoConsultaRepositorio, JpaMapeador mapeador, EnderecoJPARepositorio enderecoRepositorio) {
        this.gestaoConsultaRepositorio = gestaoConsultaRepositorio;
        this.mapeador = mapeador;
        this.enderecoRepositorio = enderecoRepositorio;
    }

    @Override
    public void salvar(Consulta consulta){
        GestaoConsultaJPA consultaJPA = mapeador.map(consulta, GestaoConsultaJPA.class);
        if(enderecoRepositorio.findById(consultaJPA.getEndereco().getNome()).isEmpty()) {
            enderecoRepositorio.save(consultaJPA.getEndereco());
        }
        gestaoConsultaRepositorio.save(consultaJPA);
    }

    @Override
    public void deletar(Consulta consulta) {
        gestaoConsultaRepositorio.delete(mapeador.map(consulta, GestaoConsultaJPA.class));
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
