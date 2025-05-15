package com.implantodontia.dominio.support.relatorio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento.TipoProcedimento;

import org.springframework.stereotype.Service;

@Service
public class HistoricoProcedimentosService {
    private final List<Procedimento> procedimentos = new ArrayList<>();
    private EstrategiaFiltroProcedimento estrategiaFiltro;

    public HistoricoProcedimentosService() {}

    public void adicionarProcedimento(Procedimento procedimento) {
        procedimentos.add(procedimento);
    }

    public void setEstrategiaFiltro(EstrategiaFiltroProcedimento estrategia) {
        this.estrategiaFiltro = estrategia;
    }

    public List<Procedimento> filtrarProcedimentos() {
        if (estrategiaFiltro == null) {
            throw new IllegalStateException("Estratégia de filtro não definida");
        }
        return estrategiaFiltro.filtrar(procedimentos);
    }

    // Mantendo os métodos antigos para compatibilidade, mas agora usando a estratégia
    public List<Procedimento> filtrarPorDataExata(LocalDate data) {
        setEstrategiaFiltro(new FiltroPorDataExata(data));
        return filtrarProcedimentos();
    }

    public List<Procedimento> filtrarPorMesAno(int mes, int ano) {
        setEstrategiaFiltro(new FiltroPorMesAno(mes, ano));
        return filtrarProcedimentos();
    }

    public List<Procedimento> filtrarPorTipo(TipoProcedimento tipo) {
        setEstrategiaFiltro(new FiltroPorTipo(tipo));
        return filtrarProcedimentos();
    }

    public void limparProcedimentos() {
        procedimentos.clear();
    }
}