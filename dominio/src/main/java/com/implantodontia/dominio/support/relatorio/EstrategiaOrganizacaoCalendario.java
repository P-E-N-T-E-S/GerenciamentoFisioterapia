package com.implantodontia.dominio.support.relatorio;

import java.util.ArrayList;
import java.util.Map;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;

public interface EstrategiaOrganizacaoCalendario {
    ArrayList<ArrayList<Consulta>> organizar(Map<String, Consulta> agenda);
}
