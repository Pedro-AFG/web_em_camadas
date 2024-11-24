package com.empresa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement
public class Aluno {
    private long matricula;
    private String nome;
    private String email;

    @JsonBackReference
    private Curso curso;

    public Aluno(long matricula, String nome, String email, Curso curso) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.curso = curso;
    }

    @JsonProperty("curso")
    public void setCursoById(Long cursoId) {
        this.curso = new Curso();
        this.curso.setId(cursoId); // Define apenas o ID inicialmente
    }
}
