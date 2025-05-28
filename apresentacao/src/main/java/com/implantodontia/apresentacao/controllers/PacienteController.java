package com.implantodontia.apresentacao.controllers;

import com.implantodontia.apresentacao.dto.EnderecoDTO;
import com.implantodontia.apresentacao.dto.PacienteDTO;
import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.enums.Cargo;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import com.implantodontia.infraestrutura.security.userdetail.UsuarioDetalhes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping()
    public ResponseEntity<String> cadastrarPaciente(@RequestBody PacienteDTO pacienteDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UsuarioDetalhes usuarioDetalhes) {
            Usuario usuario =  usuarioDetalhes.getUsuario();
            pacienteService.cadastrarPaciente(new Paciente(null, new Cpf(pacienteDto.cpf()), convertEndereco(pacienteDto.endereco()), pacienteDto.nome(), pacienteDto.contato(), pacienteDto.medicoResponsavel()), usuario.getCargo() == Cargo.ASSISTENTE);
            return new ResponseEntity<>("Paciente Cadastrado", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Usuário não autenticado", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(new PacienteId(id)));
    }

    private Endereco convertEndereco(EnderecoDTO enderecoDTO) {
        return new Endereco(
                enderecoDTO.logradouro(),
                enderecoDTO.numero(),
                enderecoDTO.complemento(),
                enderecoDTO.cidade(),
                enderecoDTO.cep()
        );
    }
}
