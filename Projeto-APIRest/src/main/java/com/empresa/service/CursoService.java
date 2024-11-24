package com.empresa.service;

import com.empresa.dao.CursoDao;
import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CursoService {

    private final CursoDao cursoDAO;

    public CursoService(CursoDao cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    public List<Curso> getAllCursos() {
        return cursoDAO.getAll();
    }

    public Curso getCurso(long id) {
        return cursoDAO.getById(id);
    }

    public void postCurso(Curso curso) {
        cursoDAO.insert(curso);
    }

    public void putCurso(Curso cursoAtualizado) {
        Curso cursoExistente = cursoDAO.getById(cursoAtualizado.getId());
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
            cursoDAO.update(cursoExistente);
        } else {
            throw new IllegalArgumentException("Curso não encontrado");
        }
    }

    public void deleteCurso(long idCurso) {
        Curso cursoToRemove = cursoDAO.getById(idCurso);
        if (cursoToRemove != null) {
            for (Aluno aluno : cursoToRemove.getAlunos()) {
                aluno.setCurso(null);
            }

            cursoDAO.delete(idCurso);
        } else {
            throw new IllegalArgumentException("Curso não encontrado");
        }
    }
}
