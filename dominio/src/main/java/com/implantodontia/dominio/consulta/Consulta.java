package com.implantodontia.dominio.consulta;

import com.implantodontia.dominio.consulta.paciente.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Consulta {
    private LocalDateTime dataHora;
    private Paciente paciente;
    private LocalDate data_vencimento;
    private boolean clientePagou;
    private String descricao;
    private Material materiais;
    private String local;

    public Consulta(LocalDateTime dataHora, String descricao, Material materiais,
                    boolean clientePagou, LocalDate dataVencimento, String local, Paciente paciente) {
        this.dataHora = dataHora;
        this.descricao = descricao;
        this.materiais = materiais;
        this.clientePagou = clientePagou;
        this.data_vencimento = dataVencimento;
        this.local = local;
        this.paciente = paciente;
    }

    public static Map<String, String> gerarLembretesConsultas(Map<String, Consulta> agenda, LocalDate hoje) {
        Map<String, String> lembretes = new HashMap<>();
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");

        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            Consulta consulta = entry.getValue();
            LocalDate dataConsulta = consulta.getDataHora().toLocalDate();

            long diasAntecedencia = ChronoUnit.DAYS.between(hoje, dataConsulta);

            if (diasAntecedencia == 1 || diasAntecedencia == 7) {
                String mensagem = "Lembrete: Consulta '" + consulta.getDescricao() + "' agendada para " +
                        dataConsulta.format(dataFormatter) + " às " +
                        consulta.getDataHora().format(horaFormatter) +
                        " em " + consulta.getLocal();
                lembretes.put(entry.getKey(), mensagem);
            }
        }
        return lembretes;
    }

    public Map<String, Consulta> filtrarPorData(Map<String, Consulta> agenda, LocalDate datafiltro) {
        Map<String, Consulta> resultado = new HashMap<>();
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            if (entry.getValue().getDataHora().toLocalDate().equals(datafiltro)) {
                resultado.put(entry.getKey(), entry.getValue());
            }
        }
        return resultado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public static Map<String, String> gerarNotificacoesPendencias(Map<String, Consulta> agenda, LocalDate hoje) {
        Map<String, String> notificacoes = new HashMap<>();

        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            Consulta consulta = entry.getValue();
            if (!consulta.getClientePagou() && consulta.getDataVencimento().isBefore(hoje)) {
                long diasAtraso = ChronoUnit.DAYS.between(consulta.getDataVencimento(), hoje);
                String mensagem = "Consulta '" + consulta.getDescricao() +
                        "' está com pagamento atrasado há " + diasAtraso + " dias.";
                notificacoes.put(entry.getKey(), mensagem);
            }
        }
        return notificacoes;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getLocal() {
        return local;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean getClientePagou() {
        return clientePagou;
    }

    public LocalDate getDataVencimento() {
        return data_vencimento;
    }

    public Material getMateriais() {
        return materiais;
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "dataHora=" + dataHora +
                ", descricao='" + descricao + '\'' +
                ", local='" + local + '\'' +
                '}';
    }
}