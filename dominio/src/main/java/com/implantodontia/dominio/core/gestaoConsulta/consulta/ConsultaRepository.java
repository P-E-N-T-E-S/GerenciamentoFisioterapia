package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository {
    void salvar(Consulta consulta);
    void deletar(Consulta consulta);
    List<Consulta> buscarConsultaPorData(LocalDate data);
    List<Consulta> listarConsultas();
    Consulta buscarPorId(Long id);
    List<Consulta> buscarConsultaPorDataVencimento(LocalDate data);
}
