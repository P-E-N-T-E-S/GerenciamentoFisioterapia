package com.implantodontia.persistencia.memoria;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaServico;
import com.implantodontia.dominio.core.material.MaterialServico;
import com.implantodontia.dominio.support.notificacoes.NotificacaoConsumidor;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.dominio.support.relatorio.procedimento.HistoricoProcedimentosService;

import java.util.HashMap;
import java.util.Map;

public class FuncionalidadesSistema {

    protected PacienteService pacienteService;
    protected NotificacaoService notificacaoService;
    protected HistoricoProcedimentosService historicoProcedimentosService;
    protected ConsultaService consultaService;
    protected FichaMedicaServico fichaMedicaServico;
    protected MaterialServico materialServico;

    public FuncionalidadesSistema() {
        Repositorio repositorio = new Repositorio();

        notificacaoService = new NotificacaoService(repositorio);
        pacienteService = new PacienteService(repositorio, notificacaoService);
        historicoProcedimentosService = new HistoricoProcedimentosService();
        fichaMedicaServico = new FichaMedicaServico(repositorio);
        materialServico = new MaterialServico(repositorio, notificacaoService);
        consultaService = new ConsultaService(repositorio, materialServico);
    }

}
