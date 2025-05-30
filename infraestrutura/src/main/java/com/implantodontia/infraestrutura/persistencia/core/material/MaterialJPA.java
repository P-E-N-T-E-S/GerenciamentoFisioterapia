package com.implantodontia.infraestrutura.persistencia.core.material;

import com.implantodontia.infraestrutura.persistencia.core.gestaoconsulta.GestaoConsultaJPA;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "material")
public class MaterialJPA {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    long id;

    int quantidade;
    String nome;

    @ManyToMany(mappedBy = "materiais")
    List<GestaoConsultaJPA> consultas;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<GestaoConsultaJPA> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<GestaoConsultaJPA> consultas) {
        this.consultas = consultas;
    }
}
