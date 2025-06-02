//package com.implantodontia.dominio.core.gestaoConsulta.consulta;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//
//import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
//import com.implantodontia.dominio.core.material.Material;
//import com.implantodontia.dominio.core.gestaoPacientes.paciente.Paciente;
//
//public class Consulta {
//    private ConsultaId consultaId;
//    private LocalDateTime dataHora;
//    private Paciente paciente;
//    private LocalDate data_vencimento;
//    private boolean clientePagou;
//    private String descricao;
//    private List<Material> materiais;
//    private Endereco local;
//    private Double valor;
//
//    public Consulta(ConsultaId consultaId, LocalDateTime dataHora, Paciente paciente, LocalDate data_vencimento, boolean clientePagou, String descricao, List<Material> materiais, Endereco local, Double valor) {
//        this.consultaId = consultaId;
//        this.dataHora = dataHora;
//        this.paciente = paciente;
//        this.data_vencimento = data_vencimento;
//        this.clientePagou = clientePagou;
//        this.descricao = descricao;
//        this.materiais = materiais;
//        this.local = local;
//        this.valor = valor != null ? valor : 0.0;
//        ;
//    }
//
//    public LocalDateTime getDataHora() {
//        return dataHora;
//    }
//
//    public Endereco getLocal() {
//        return local;
//    }
//
//    public String getDescricao() {
//        return descricao;
//    }
//
//    public boolean getClientePagou() {
//        return clientePagou;
//    }
//
//    public boolean isClientePagou() {
//        return clientePagou;
//    }
//
//    public void setClientePagou(boolean clientePagou) {
//        this.clientePagou = clientePagou;
//    }
//
//    public LocalDate getDataVencimento() {
//        return data_vencimento;
//    }
//
//    public List<Material> getMateriais() {
//        return materiais;
//    }
//
//    public Paciente getPaciente() {
//        return this.paciente;
//    }
//
//    public ConsultaId getConsultaId() {
//        return consultaId;
//    }
//
//    public void setConsultaId(ConsultaId consultaId) {
//        this.consultaId = consultaId;
//    }
//
//    public double getValor() {
//        return valor;
//    }
//
//    public void setValor(double valor) {
//        this.valor = valor;
//    }
//
//    public void setMateriais(List<Material> materiais) {
//        this.materiais = materiais;
//    }
//}
