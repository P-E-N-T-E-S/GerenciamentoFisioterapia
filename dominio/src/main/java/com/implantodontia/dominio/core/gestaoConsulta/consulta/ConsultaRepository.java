package com.implantodontia.dominio.core.gestaoConsulta.consulta;

import java.time.LocalDate;
import java.util.List;

public interface ConsultaRepository {
    void salvar(Consulta consulta);
    List<Consulta> buscarPorData(LocalDate data);
}
