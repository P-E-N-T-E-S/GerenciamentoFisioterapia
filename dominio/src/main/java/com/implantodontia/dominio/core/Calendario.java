package com.implantodontia.dominio.core;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;

import java.lang.reflect.Array;
import java.time.Month;
import java.util.ArrayList;
import java.util.Map;

public class Calendario {

    public ArrayList<ArrayList<Consulta>> setupCalendario(Map<String, Consulta> agenda) {
        ArrayList<ArrayList<Consulta>> calendarioPorMes = new ArrayList<>(12);

        for(int i = 0; i < 12; i++) {
            calendarioPorMes.add(new ArrayList<>());
        }

        if (agenda == null) {
            return calendarioPorMes;
        }

        ArrayList<ArrayList<Consulta>> lista = new ArrayList<>();
        agenda.values().forEach(consulta -> {
            int mes = consulta.getDataHora().getMonthValue() - 1;
            calendarioPorMes.get(mes).add(consulta);
        });

        return calendarioPorMes;
    }


}
