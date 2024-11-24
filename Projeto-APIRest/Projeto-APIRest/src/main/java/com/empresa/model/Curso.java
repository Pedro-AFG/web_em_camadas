package com.empresa.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement
public class Curso {
    private long id;
    private String nome;
    private List<Aluno> alunos;

    @JsonCreator
    public Curso(@JsonProperty("id") long id,
                 @JsonProperty("nome") String nome,
                 @JsonProperty("alunos") List<Aluno> alunos) {
        this.id = id;
        this.nome = nome;
        this.alunos = alunos != null ? alunos : new ArrayList<>();
    }

    public Curso() {
        this.alunos = new ArrayList<>();
    }
}
