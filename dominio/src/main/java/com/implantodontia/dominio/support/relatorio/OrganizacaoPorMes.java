import java.util.ArrayList;
import java.util.Map;

import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;

public class OrganizacaoPorMes implements EstrategiaOrganizacaoCalendario {
    @Override
    public ArrayList<ArrayList<Consulta>> organizar(Map<String, Consulta> agenda) {
        ArrayList<ArrayList<Consulta>> calendarioPorMes = new ArrayList<>(12);
        
        for(int i = 0; i < 12; i++) {
            calendarioPorMes.add(new ArrayList<>());
        }

        if (agenda == null) {
            return calendarioPorMes;
        }

        agenda.values().forEach(consulta -> {
            int mes = consulta.getDataHora().getMonthValue() - 1;
            calendarioPorMes.get(mes).add(consulta);
        });

        return calendarioPorMes;
    }
}

// TODO: Nova implementação (por semana)