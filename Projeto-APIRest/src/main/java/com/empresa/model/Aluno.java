package com.empresa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@Entity
public class Aluno {

    @Id
    private Long matricula;

    private String nome;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    @JsonBackReference
    private Curso curso;

    public Aluno(Long matricula, String nome, String email, Curso curso) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
    }

    public Aluno() {}

    @JsonProperty("curso")
    public void setCursoById(Long cursoId) {
        this.curso = new Curso();
        this.curso.setId(cursoId);
    }
}
