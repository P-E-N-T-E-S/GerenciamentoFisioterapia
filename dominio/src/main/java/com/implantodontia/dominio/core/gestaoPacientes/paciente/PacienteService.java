package com.implantodontia.dominio.core.gestaoPacientes.paciente;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedica;
import com.implantodontia.dominio.support.notificacoes.NotificacaoService;
import com.implantodontia.dominio.support.notificacoes.enums.TipoNotificacao;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.validation.AbstractFichaMedicaValidator;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.validation.ValidadorFichaMedicaPadrao;

import org.springframework.stereotype.Service;

public class PacienteService {

    private PacienteRepository pacienteRepository;

    private NotificacaoService notificacaoService;

    public PacienteService(PacienteRepository pacienteRepository, NotificacaoService notificacaoService) {
        this.pacienteRepository = pacienteRepository;
        this.notificacaoService = notificacaoService;
    }

    public void cadastrarPaciente(Paciente paciente, boolean notificarUsuario) {
        if (paciente.getContato() == null || paciente.getContato().isBlank()) {
            throw new IllegalArgumentException("Contato obrigatório");
        }
        pacienteRepository.cadastrar(paciente);
        if (notificarUsuario) {
            String notificacao = String.format(
                    "Alerta: Novo paciente cadastrado por %s. Nome: %s, Contato: %s",
                    paciente.getMedicoResponsavel(),
                    paciente.getNome(),
                    paciente.getContato()
            );
            notificacaoService.notificarUsuario("fisioterapeuta@email", notificacao, TipoNotificacao.CLIENTE_NOVO);
        }
    }

    public Paciente buscarPacientePorId(PacienteId id) {
        return pacienteRepository.buscarPorId(id);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.listarPacientes();
    }

    public boolean gerarPdfFichaMedica(FichaMedica ficha) {
        Paciente paciente = buscarPacientePorId(ficha.getPacienteId());

        if (paciente == null) {
            throw new IllegalArgumentException("Paciente não encontrado");
        }

        // Implementação da geração do PDF usando o paciente encontrado
        return ficha.validarDadosObrigatorios();
    }


    public boolean simularGeracaoPdf(PacienteId pacienteId) {
        // Exemplo em alguma classe de serviço
        FichaMedica fichaParaValidar = obterFichaMedica(pacienteId);

        AbstractFichaMedicaValidator validador = new ValidadorFichaMedicaPadrao(); // Ou outra subclasse específica
        boolean isFichaValida = validador.validar(fichaParaValidar);

        // if (isFichaValida) {
        //     System.out.println("A ficha médica é válida, prosseguindo com a operação...");
        //     // Prossiga com a lógica que depende de uma ficha válida
        // } else {
        //     System.err.println("A ficha médica é inválida. Operação não pode continuar.");
        //     // Trate o caso de ficha inválida
        // }

            return fichaParaValidar != null && isFichaValida;
        }

    public FichaMedica obterFichaMedica(PacienteId pacienteId) {
        Paciente paciente = buscarPacientePorId(pacienteId);
        if (paciente != null) {
            return paciente.getFichaMedica();
        }
        throw new IllegalArgumentException("Paciente não encontrado com ID: " + pacienteId.getId());
    }

    public boolean deveSolicitarObservacoes(PacienteId pacienteId) {
        FichaMedica ficha = obterFichaMedica(pacienteId);
        return ficha.getObservacoes() == null ||
                ficha.getObservacoes().isBlank();
    }

    public void editarPaciente(Paciente paciente) {
        Paciente pacienteAtual = pacienteRepository.buscarPorId(paciente.getPacienteId());
        if(pacienteAtual == null){
            return;
        }

        paciente.setFichaMedica(pacienteAtual.getFichaMedica());
        pacienteRepository.atualizar(paciente);
    }

    public List<Paciente> pesquisarPorNome(String nome) {
        return pacienteRepository.pesquisarPorNome(nome);
    }

    public void excluirPaciente(PacienteId pacienteId) {
        pacienteRepository.deletar(pacienteId);
    }
}
