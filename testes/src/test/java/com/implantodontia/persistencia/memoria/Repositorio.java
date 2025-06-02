package com.implantodontia.persistencia.memoria;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteRepository;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaImplanta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaRepositorio;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaServico;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialRepository;
import com.implantodontia.dominio.core.material.MaterialServico;
import com.implantodontia.dominio.support.notificacoes.Notificacao;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.relatorio.procedimento.HistoricoProcedimentosService;
import io.cucumber.java.it.Ma;
import org.mockito.internal.matchers.NotNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class Repositorio implements PacienteRepository, ConsultaRepository, ProcedimentoRepository, FichaMedicaRepositorio, MaterialRepository, NotificacaoConsumidor {

    protected Map<Long, Paciente> pacientes = new HashMap<>();
    protected Map<Long, Consulta> consultas = new HashMap<>();
    protected List<Notificacao> notificacoes = new ArrayList<>();

    @Override
    public void salvar(Consulta consulta) {
        consultas.put(consulta.getConsultaId().getId() ,consulta);
    }

    @Override
    public void deletar(Consulta consulta) {

    }

    @Override
    public void salvar(Procedimento procedimento) {

    }

    @Override
    public void salvar(Material material) {

    }

    @Override
    public void deletar(Long id) {

    }

    @Override
    public void editar(Material material, long id) {

    }

    @Override
    public void editarPorNome(Material material, String nome) {

    }

    @Override
    public Material buscarPorNome(String nome) {
        return null;
    }

    @Override
    public List<Material> buscarTodos() {
        return null;
    }

    @Override
    public List<Procedimento> buscarTodosProcedimentos() {
        return null;
    }

    @Override
    public List<Procedimento> buscarPorData(LocalDate data) {
        return null;
    }

    @Override
    public List<Consulta> buscarConsultaPorData(LocalDate data) {
        return null;
    }

    @Override
    public List<Procedimento> buscarPorTipo(TipoProcedimento tipo) {
        return null;
    }

    @Override
    public List<Procedimento> buscarPorDataETipo(LocalDate data, TipoProcedimento tipo) {
        return null;
    }

    @Override
    public List<Consulta> listarConsultas() {
        List<Consulta> busca_consultas = new ArrayList<>(consultas.values());
        return busca_consultas;
    }

    @Override
    public Consulta buscarPorId(Long id) {
        return null;
    }

    @Override
    public void cadastrar(Paciente paciente) {
        pacientes.put(paciente.getPacienteId().getId(), paciente);
    }

    @Override
    public Paciente buscarPorId(PacienteId id) {
        Paciente paciente = pacientes.get(id.getId());
        return paciente;
    }

    @Override
    public void deletar(PacienteId id) {

    }

    @Override
    public void atualizar(Paciente paciente) {

    }

    @Override
    public List<Paciente> pesquisarPorNome(String nome) {
        return null;
    }

    @Override
    public List<Paciente> listarPacientes() {
        List<Paciente> pacientes_lista = new ArrayList<>(pacientes.values());

        return pacientes_lista;
    }

    @Override
    public void salvar(FichaMedicaImplanta fichaMedica) {

    }

    @Override
    public void deletar(FichaMedicaImplanta fichaMedica) {

    }

    @Override
    public void editar(FichaMedicaImplanta fichaMedica, long id) {

    }

    @Override
    public List<FichaMedicaImplanta> listarFichaMedica() {
        return null;
    }

    @Override
    public FichaMedicaImplanta buscarPorId(long id) {
        return null;
    }

    @Override
    public FichaMedicaImplanta buscarPorPaciente(PacienteId idPaciente) {
        return null;
    }

    @Override
    public List<Notificacao> consumirMensagens() {
        return null;
    }
}
