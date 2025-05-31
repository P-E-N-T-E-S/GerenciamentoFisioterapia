package com.implantodontia.apresentacao.controllers;

import com.implantodontia.apresentacao.dto.MaterialDTO;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialServico;
import com.implantodontia.dominio.core.material.exceptions.MaterialNaoEncontradoException;
import com.implantodontia.dominio.core.material.exceptions.QuantidadeIndisponivelException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/material")
public class MaterialController {

    private MaterialServico materialServico;

    public MaterialController(MaterialServico materialServico) {
        this.materialServico = materialServico;
    }

    @PutMapping("/adicionar")
    public ResponseEntity<String> adicionar(@RequestBody MaterialDTO materialDTO) {
        materialServico.adicionar(materialDTO.nome(), materialDTO.quantidade());
        return new ResponseEntity<>("Material atualizado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping("/remover")
    public ResponseEntity<String> remover(@RequestBody MaterialDTO materialDTO){
        try {
            materialServico.remover(materialDTO.nome(), materialDTO.quantidade());
            return new ResponseEntity<>("Material atualizado com sucesso!", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizar(@RequestBody MaterialDTO materialDTO, @PathVariable("id") Long id){
        materialServico.editar(materialDTO.nome(), materialDTO.quantidade(), id);
        return new ResponseEntity<>("Material atualizado com sucesso!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remover(@PathVariable("id") long id){
        materialServico.deletarPorId(id);
        return new ResponseEntity<>("Material removido com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<Iterable<Material>> listarMateriais(){
        return ResponseEntity.ok(materialServico.listarMateriais());
    }
}
