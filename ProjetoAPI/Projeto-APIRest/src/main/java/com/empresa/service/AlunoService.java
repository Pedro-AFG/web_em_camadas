package com.empresa.service;

import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    private List<Aluno> alunoList = new ArrayList<>();
    private final CursoService cursoService;

    public AlunoService(CursoService cursoService) {
        this.cursoService = cursoService;
        Curso cursoMatematica = cursoService.getCurso(101);
        Curso cursoPortugues = cursoService.getCurso(102);

        Aluno aluno1 = new Aluno(1, "Ana", "ana@gmail.com", cursoMatematica);
        Aluno aluno2 = new Aluno(2, "Carlos", "carlos@gmail.com", cursoPortugues);

        alunoList.add(aluno1);
        alunoList.add(aluno2);

        cursoMatematica.getAlunos().add(aluno1);
        cursoPortugues.getAlunos().add(aluno2);
    }

    public List<Aluno> getAllAlunos() {
        return alunoList;
    }

    public Aluno getAluno(long matricula) {
        return alunoList.stream().filter(a -> a.getMatricula() == matricula).findFirst().orElse(null);
    }

    public void postAluno(Aluno aluno) {
        alunoList.add(aluno);

        Curso curso = cursoService.getCurso(aluno.getCurso().getId());
        if (curso != null) {
            aluno.setCurso(curso);
            curso.getAlunos().add(aluno);
        } else {
            throw new IllegalArgumentException("Curso não encontrado");
        }
    }

    public void putAluno(Aluno alunoAtualizado) {
        Aluno alunoExistente = getAluno(alunoAtualizado.getMatricula());

        if (alunoExistente != null) {
            alunoExistente.setNome(alunoAtualizado.getNome());
            alunoExistente.setEmail(alunoAtualizado.getEmail());

            Curso cursoAtual = alunoExistente.getCurso();
            Curso novoCurso = cursoService.getCurso(alunoAtualizado.getCurso().getId());

            if (novoCurso != null) {
                if (cursoAtual != null) {
                    cursoAtual.getAlunos().remove(alunoExistente);
                }

                alunoExistente.setCurso(novoCurso);
                novoCurso.getAlunos().add(alunoExistente);
            } else {
                throw new IllegalArgumentException("Curso não encontrado");
            }
        } else {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
    }

    public void deleteAluno(long matricula) {
        Aluno alunoToRemove = getAluno(matricula);
        if (alunoToRemove != null) {
            alunoList.remove(alunoToRemove);

            Curso curso = alunoToRemove.getCurso();
            if (curso != null && curso.getAlunos() != null) {
                curso.getAlunos().remove(alunoToRemove);

                alunoToRemove.setCurso(null);
            }
        }
    }
}
