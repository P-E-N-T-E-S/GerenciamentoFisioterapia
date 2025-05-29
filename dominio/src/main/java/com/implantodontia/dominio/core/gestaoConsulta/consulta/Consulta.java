package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;


import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;

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

    public boolean isClientePagou() {
        return clientePagou;
    }

    public void setClientePagou(boolean clientePagou) {
        this.clientePagou = clientePagou;
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

    public Paciente getPaciente() {
        return this.paciente;
    }
}
