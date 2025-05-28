package com.implantodontia.apresentacao.controllers;

import com.implantodontia.apresentacao.dto.ConsultaDTO;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteId;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private ConsultaService consultaService;
    private PacienteService pacienteService;

    public ConsultaController(ConsultaService consultaService, PacienteService pacienteService) {
        this.consultaService = consultaService;
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<String> salvarConsulta(@RequestBody ConsultaDTO consulta){
        consultaService.salvar(new Consulta(consulta.dataHora(),
                consulta.descricao(),
                null,
                false,
                null,
                consulta.local(),
                pacienteService.buscarPacientePorId(new PacienteId(consulta.pacienteId()))));

        return new ResponseEntity<>("Consulta salva com sucesso", HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Consulta>> listarConsultas(){
        return ResponseEntity.ok(consultaService.listarConsultas());
    }

    @GetMapping("/data")
    public ResponseEntity<List<Consulta>> buscarPorData(@RequestParam("data") String data){
        LocalDate dataHora = LocalDate.from(LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        return ResponseEntity.ok(consultaService.buscarPorData(dataHora));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPorId(@PathVariable Long id){
        consultaService.deletarPorId(id);
        return new ResponseEntity<>("Consulta deletada com sucesso", HttpStatus.OK);
    }
}
