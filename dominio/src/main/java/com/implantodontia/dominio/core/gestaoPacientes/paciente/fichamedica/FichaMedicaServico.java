package com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica;

import java.time.LocalDateTime;

public class FichaMedicaServico {

    public void preencherDadosClinicos(FichaMedica fichaMedica, String historicoMedico, String alergias, LocalDateTime now) {
        fichaMedica.setHistoricoMedico(historicoMedico);
        fichaMedica.setAlergias(alergias);
        fichaMedica.setUltimaAtualizacao(now);
    }

    public boolean validarDadosObrigatorios(FichaMedica fichaMedica){
        return fichaMedica.getHistoricoMedico() != null && !fichaMedica.getHistoricoMedico().isBlank()
                && fichaMedica.getAlergias() != null && !fichaMedica.getAlergias().isBlank();
    }

    public void adicionarObservacao(FichaMedica fichaMedica, String observacao) {
        fichaMedica.setObservacoes(observacao);
        fichaMedica.setUltimaAtualizacao(LocalDateTime.now());
        // :) Se esse metodo não funcionar a culpa é de @Pedro
    }
}
