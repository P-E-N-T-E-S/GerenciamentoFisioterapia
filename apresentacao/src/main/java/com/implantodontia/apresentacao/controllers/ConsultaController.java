package com.implantodontia.apresentacao.controllers;

import com.implantodontia.apresentacao.dto.ConsultaDTO;
import com.implantodontia.apresentacao.dto.EnderecoDTO;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.Consulta;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaId;
import com.implantodontia.dominio.core.gestaoConsulta.consulta.ConsultaService;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.Endereco;
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
        consultaService.salvar(new Consulta(new ConsultaId(0L), consulta.dataHora(),
                pacienteService.buscarPacientePorId(new PacienteId(consulta.pacienteId())),
                null,
                false,
                consulta.descricao(),
                null,
                convertEndereco(consulta.endereco())));

        return new ResponseEntity<>("Consulta salva com sucesso", HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Consulta>> listarConsultas(){
        return ResponseEntity.ok(consultaService.listarConsultas());
    }

    @GetMapping("/data")
    public ResponseEntity<List<Consulta>> buscarPorData(@RequestParam("data") String data){
        LocalDate dataHora = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return ResponseEntity.ok(consultaService.buscarPorData(dataHora));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable("id") long id){
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPorId(@PathVariable("id") long id){
        consultaService.deletarPorId(id);
        return new ResponseEntity<>("Consulta deletada com sucesso", HttpStatus.OK);
    }

    protected Endereco convertEndereco(EnderecoDTO enderecoDTO) {
        return new Endereco(
                enderecoDTO.nome(),
                enderecoDTO.logradouro(),
                enderecoDTO.numero(),
                enderecoDTO.complemento(),
                enderecoDTO.cidade(),
                enderecoDTO.cep()
        );
    }
}
