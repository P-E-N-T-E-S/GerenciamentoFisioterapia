package com.implantodontia.apresentacao.controllers;

import com.implantodontia.apresentacao.dto.EnderecoDTO;
import com.implantodontia.apresentacao.dto.FichaMedicaDTO;
import com.implantodontia.apresentacao.dto.PacienteDTO;
import com.implantodontia.dominio.core.adm.Usuario;
import com.implantodontia.dominio.core.adm.enums.Cargo;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.*;
import com.implantodontia.dominio.core.gestaoPacientes.paciente.fichamedica.FichaMedicaServico;
import com.implantodontia.infraestrutura.security.userdetail.UsuarioDetalhes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private PacienteService pacienteService;
    private FichaMedicaServico fichaMedicaServico;

    public PacienteController(PacienteService pacienteService, FichaMedicaServico fichaMedicaServico) {
        this.pacienteService = pacienteService;
        this.fichaMedicaServico = fichaMedicaServico;
    }

    @PostMapping()
    public ResponseEntity<String> cadastrarPaciente(@RequestBody PacienteDTO pacienteDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UsuarioDetalhes usuarioDetalhes) {
            Usuario usuario =  usuarioDetalhes.getUsuario();
            pacienteService.cadastrarPaciente(new Paciente(new PacienteId(0), new Cpf(pacienteDto.cpf()), convertEndereco(pacienteDto.endereco()), pacienteDto.nome(), pacienteDto.contato(), pacienteDto.medicoResponsavel()), usuario.getCargo() == Cargo.ASSISTENTE);
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
    public ResponseEntity<Paciente> buscarPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(new PacienteId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editarPorId(@PathVariable("id") Long id, @RequestBody PacienteDTO pacienteDto) {
        pacienteService.editarPaciente(new Paciente(new PacienteId(id),
                new Cpf(pacienteDto.cpf()),
                        convertEndereco(pacienteDto.endereco()),
                        pacienteDto.nome(),
                        pacienteDto.contato(),
                        pacienteDto.medicoResponsavel()));
        return new ResponseEntity<>("Paciente Atualizado", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPorId(@PathVariable("id") Long id){
        pacienteService.excluirPaciente(new  PacienteId(id));
        return new ResponseEntity<>("Paciente Excluido", HttpStatus.OK);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Paciente>> pesquisarPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(pacienteService.pesquisarPorNome(nome));
    }

    @PutMapping("/ficha")
    public ResponseEntity<String> adicionarFichaMedica(@RequestBody FichaMedicaDTO ficha){
        fichaMedicaServico.preencherDadosClinicos(new PacienteId(ficha.id()), ficha.historicoMedico(), ficha.alergias());
        if(ficha.observacoes() != null && !ficha.observacoes().isBlank()){
            fichaMedicaServico.adicionarObservacao(new PacienteId(ficha.id()), ficha.observacoes());
        }
        return new ResponseEntity<>("Ficha atualizada com sucesso!", HttpStatus.CREATED);
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
