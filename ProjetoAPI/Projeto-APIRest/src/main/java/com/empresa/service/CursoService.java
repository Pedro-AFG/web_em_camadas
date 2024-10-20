package com.empresa.service;

import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService {

    private List<Curso> cursoList = new ArrayList<>();

    public CursoService() {
        Curso curso1 = new Curso(101, "Matemática", new ArrayList<>());
        Curso curso2 = new Curso(102, "Português", new ArrayList<>());

        cursoList.add(curso1);
        cursoList.add(curso2);
    }

    public List<Curso> getAllCursos() {
        return cursoList;
    }

    public Curso getCurso(long id) {
        return cursoList.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public void postCurso(Curso curso) {
        cursoList.add(curso);
    }

    public void putCurso(Curso cursoAtualizado) {
        Curso cursoExistente = getCurso(cursoAtualizado.getId());
        if (cursoExistente != null) {
            cursoExistente.setNome(cursoAtualizado.getNome());

            for (Aluno aluno : cursoExistente.getAlunos()) {
                aluno.setCurso(null);
            }

            List<Aluno> alunosAtualizados = cursoAtualizado.getAlunos();
            for (Aluno aluno : alunosAtualizados) {
                aluno.setCurso(cursoExistente);
            }

            cursoExistente.setAlunos(alunosAtualizados);
        } else {
            throw new IllegalArgumentException("Curso não encontrado");
        }
    }

    public void deleteCurso(long idCurso) {
        Curso cursoToRemove = getCurso(idCurso);
        if (cursoToRemove != null) {
            for (Aluno aluno : cursoToRemove.getAlunos()) {
                aluno.setCurso(null);
            }

            cursoList.remove(cursoToRemove);
        } else {
            throw new IllegalArgumentException("Curso não encontrado");
        }
    }
}
