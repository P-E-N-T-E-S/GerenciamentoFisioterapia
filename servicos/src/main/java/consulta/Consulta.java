package consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Consulta {
    LocalDate data;
    LocalDate data_vencimento;
    boolean clientePagou;
    String descricao;
    Material materiais;

    Map<String, Consulta> agenda = new HashMap<>();

    public Consulta(LocalDate data, String descricao, Material materiais, boolean clientePagou,
                    LocalDate dataVencimento) {
        this.data = data;
        this.descricao = descricao;
        this.materiais = materiais;
        this.clientePagou = clientePagou;
        this.data_vencimento = dataVencimento;
    }

    public Map<String, Consulta> filtrarPorData(Map<String, Consulta> agenda, LocalDate datafiltro) {
        Map<String, Consulta> resultado = new HashMap<>();
        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            if (entry.getValue().getData().equals(datafiltro)) {
                resultado.put(entry.getKey(), entry.getValue());
            }
        }
        return resultado;
    }

    public static Map<String, String> gerarNotificacoesPendencias(Map<String, Consulta> agenda, LocalDate hoje) {
        Map<String, String> notificacoes = new HashMap<>();

        for (Map.Entry<String, Consulta> entry : agenda.entrySet()) {
            Consulta consulta = entry.getValue();
            if (!consulta.getClientePagou() && consulta.getDataVencimento().isBefore(hoje)) {
                long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(consulta.getDataVencimento(), hoje);
                String mensagem = "Consulta '" + consulta.getDescricao() +
                        "' está com pagamento atrasado há " + diasAtraso + " dias.";
                notificacoes.put(entry.getKey(), mensagem);
            }
        }

        return notificacoes;
    }

    public LocalDate getData() {
        return data;
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
                "data=" + data +
                ", descricao='" + descricao + '\'' +
                ", materiais=" + materiais +
                '}';
    }

}

