package com.implantodontia.dominio.support.relatorio.procedimento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Procedimento;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.TipoProcedimento;
import com.implantodontia.dominio.support.relatorio.EstrategiaFiltroProcedimento;
import com.implantodontia.dominio.support.relatorio.FiltroPorDataExata;
import com.implantodontia.dominio.support.relatorio.FiltroPorMesAno;
import com.implantodontia.dominio.support.relatorio.FiltroPorTipo;

import org.springframework.stereotype.Service;

@Service
public class HistoricoProcedimentosService implements ProcedimentoCollection {
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

    @Override
    public ProcedimentoIterator createIterator() {
        return new ProcedimentoPorDataIterator(procedimentos, LocalDate.now());
    }

    // Método para criar iterador específico por data
    public ProcedimentoIterator createIteratorPorData(LocalDate data) {
        return new ProcedimentoPorDataIterator(procedimentos, data);
    }

    // Método para criar iterador específico por tipo
    public ProcedimentoIterator createIteratorPorTipo(TipoProcedimento tipo) {
        return new ProcedimentoPorTipoIterator(procedimentos, tipo);
    }

    // Exemplo de uso do iterador
    public List<Procedimento> listarProcedimentosPorData(LocalDate data) {
        List<Procedimento> resultado = new ArrayList<>();
        ProcedimentoIterator iterator = createIteratorPorData(data);
        
        while (iterator.hasNext()) {
            resultado.add(iterator.next());
        }
        
        return resultado;
    }

    public List<Procedimento> listarProcedimentosPorTipo(TipoProcedimento tipo) {
        List<Procedimento> resultado = new ArrayList<>();
        ProcedimentoIterator iterator = createIteratorPorTipo(tipo);
        
        while (iterator.hasNext()) {
            resultado.add(iterator.next());
        }
        
        return resultado;
    }

    public void limparProcedimentos() {
        procedimentos.clear();
    }
}