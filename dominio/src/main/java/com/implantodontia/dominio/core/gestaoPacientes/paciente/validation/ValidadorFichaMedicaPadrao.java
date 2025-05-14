package com.implantodontia.dominio.core.gestaoPacientes.paciente.validation;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.FichaMedica;
import java.time.LocalDateTime;

public class ValidadorFichaMedicaPadrao extends AbstractFichaMedicaValidator {

    @Override
    protected boolean executarValidacoesEspecificas(FichaMedica fichaMedica) {
        // Exemplo de validação específica: verificar se a 'ultimaAtualizacao' não é muito antiga.
        if (fichaMedica.getUltimaAtualizacao() != null &&
            fichaMedica.getUltimaAtualizacao().isBefore(LocalDateTime.now().minusYears(2))) {
            System.err.println("Detalhe Validação Específica: Ficha médica não é atualizada há mais de 2 anos (" + fichaMedica.getUltimaAtualizacao() + "). Considerar atualização.");
            // Se isso fosse um erro crítico, retornaria false. Por ora, é um aviso.
        }
        
        // Outro exemplo: Se o paciente fosse de um tipo que exige observações.
        // (Isso exigiria acesso ao objeto Paciente, o que não é direto aqui)
        // if (pacienteAssociado.getTipo() == TipoPaciente.CRONICO && 
        //     (fichaMedica.getObservacoes() == null || fichaMedica.getObservacoes().isBlank())) {
        //     System.err.println("Detalhe Validação Específica: Observações são mandatórias para este tipo de paciente.");
        //     return false;
        // }
        
        System.out.println("Validações específicas para Ficha Médica Padrão executadas.");
        return true;
    }

    @Override
    protected boolean executarVerificacoesAdicionaisHook(FichaMedica fichaMedica) {
        // Chama a implementação da superclasse primeiro, se houver lógica útil lá.
        boolean superHookValido = super.executarVerificacoesAdicionaisHook(fichaMedica);
        if(!superHookValido) {
            return false;
        }

        // Verificação adicional: 'ultimaAtualizacao' não deve estar no futuro.
        if (fichaMedica.getUltimaAtualizacao() != null && fichaMedica.getUltimaAtualizacao().isAfter(LocalDateTime.now())) {
            System.err.println("Detalhe Hook: Data da última atualização (" + fichaMedica.getUltimaAtualizacao() + ") é uma data futura.");
            return false;
        }
        
        System.out.println("Verificações adicionais (hook) para Ficha Médica Padrão executadas.");
        return true;
    }
}
