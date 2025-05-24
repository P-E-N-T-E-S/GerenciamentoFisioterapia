package com.implantodontia.dominio.core.gestaoPacientes.paciente.validation;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;

public abstract class AbstractFichaMedicaValidator {

    /**
     * Template Method: Define o esqueleto do algoritmo de validação.
     * @param fichaMedica A instância de FichaMedica a ser validada.
     * @return true se a ficha for válida, false caso contrário.
     */
    public final boolean validar(FichaMedica fichaMedica) {
        if (fichaMedica == null) {
            System.err.println("VALIDAÇÃO FALHOU: Instância de FichaMedica não pode ser nula.");
            return false;
        }

        if (!validarEstruturaFundamental(fichaMedica)) {
            System.err.println("VALIDAÇÃO FALHOU: Estrutura fundamental da ficha médica inválida.");
            return false;
        }

        if (!validarConteudoClinicoEssencial(fichaMedica)) {
            System.err.println("VALIDAÇÃO FALHOU: Conteúdo clínico essencial incompleto ou inválido.");
            return false;
        }

        if (!executarValidacoesEspecificas(fichaMedica)) {
            System.err.println("VALIDAÇÃO FALHOU: Regras específicas de validação não atendidas.");
            return false;
        }

        if (!executarVerificacoesAdicionaisHook(fichaMedica)) {
            System.err.println("VALIDAÇÃO FALHOU: Verificações adicionais (hook) não atendidas.");
            return false;
        }

        System.out.println("VALIDAÇÃO BEM-SUCEDIDA: Ficha médica é válida.");
        return true;
    }

    /**
     * Passo concreto do template: Valida a estrutura básica e IDs.
     */
    protected boolean validarEstruturaFundamental(FichaMedica fichaMedica) {
        if (fichaMedica.getPacienteId() == null) {
            System.err.println("Detalhe Validação: ID do Paciente na ficha médica não pode ser nulo.");
            return false;
        }
        // A validação de ultimaAtualizacao pode depender se ela pode ser nula inicialmente.
        // Se for obrigatória após qualquer modificação, o if abaixo seria pertinente.
        /*
        if (fichaMedica.getUltimaAtualizacao() == null) {
             System.err.println("Detalhe Validação: Data da última atualização na ficha médica não pode ser nula.");
             return false;
        }
        */
        return true;
    }

    /**
     * Passo concreto do template: Valida campos clínicos obrigatórios.
     * Esta lógica é similar ao método FichaMedica.validarDadosObrigatorios().
     */
    protected boolean validarConteudoClinicoEssencial(FichaMedica fichaMedica) {
        boolean valido = fichaMedica.getHistoricoMedico() != null && !fichaMedica.getHistoricoMedico().isBlank()
                      && fichaMedica.getAlergias() != null && !fichaMedica.getAlergias().isBlank();
        if (!valido) {
            System.err.println("Detalhe Validação: Histórico médico e/ou alergias não preenchidos.");
        }
        return valido;
    }

    /**
     * Passo abstrato: Deve ser implementado por subclasses para validações específicas.
     */
    protected abstract boolean executarValidacoesEspecificas(FichaMedica fichaMedica);

    /**
     * Hook method: Subclasses podem sobrescrever para adicionar verificações opcionais.
     * Fornece uma implementação padrão.
     */
    protected boolean executarVerificacoesAdicionaisHook(FichaMedica fichaMedica) {
        if (fichaMedica.getObservacoes() == null || fichaMedica.getObservacoes().isBlank()) {
            System.out.println("Aviso (Hook): Ficha médica sem observações adicionais registradas.");
        }
        return true; // Por padrão, a ausência de observações não invalida.
    }
}
