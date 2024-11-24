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

    public Curso postCurso(Curso curso) {
        // Ajusta relações entre curso e alunos antes de salvar
        for (Aluno aluno : curso.getAlunos()) {
            aluno.setCurso(curso);
        }
        return cursoDAO.insert(curso);
    }

    public Curso putCurso(Curso cursoAtualizado) {
        Curso cursoExistente = cursoDAO.getById(cursoAtualizado.getId());
        if (cursoExistente == null) {
            throw new IllegalArgumentException("Curso com ID " + cursoAtualizado.getId() + " não encontrado");
        }

        cursoExistente.setNome(cursoAtualizado.getNome());

        // Atualiza relação com alunos
        for (Aluno aluno : cursoAtualizado.getAlunos()) {
            aluno.setCurso(cursoExistente);
        }
        cursoExistente.setAlunos(cursoAtualizado.getAlunos());

        return cursoDAO.update(cursoExistente);
    }

    public void deleteCurso(long idCurso) {
        Curso cursoToRemove = cursoDAO.getById(idCurso);
        if (cursoToRemove != null) {
            // Remove associação dos alunos com o curso
            for (Aluno aluno : cursoToRemove.getAlunos()) {
                aluno.setCurso(null);
            }
            cursoDAO.delete(idCurso);
        } else {
            throw new IllegalArgumentException("Curso com ID " + idCurso + " não encontrado");
        }
    }
}
