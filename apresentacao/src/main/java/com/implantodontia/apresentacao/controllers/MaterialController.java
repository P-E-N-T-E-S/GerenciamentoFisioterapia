package com.implantodontia.apresentacao.controllers;

import com.implantodontia.apresentacao.dto.MaterialDTO;
import com.implantodontia.dominio.core.material.Material;
import com.implantodontia.dominio.core.material.MaterialServico;
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

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody MaterialDTO materialDTO) {
        materialServico.adicionar(materialDTO.nome(), materialDTO.quantidade());
        return new ResponseEntity<>("Material atualizado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> remover(@RequestBody MaterialDTO materialDTO){
        materialServico.remover(materialDTO.nome(), materialDTO.quantidade());
        return new ResponseEntity<>("Material atualizado com sucesso!", HttpStatus.OK);
    }
}
