package com.empresa.service;

import com.empresa.dao.AlunoDao;
import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlunoService {

    private final AlunoDao alunoDAO;
    private final CursoService cursoService;

    public AlunoService(AlunoDao alunoDAO, CursoService cursoService) {
        this.alunoDAO = alunoDAO;
        this.cursoService = cursoService;
    }

    public List<Aluno> getAllAlunos() {
        return alunoDAO.getAll();
    }

    public Aluno getAluno(long matricula) {
        return alunoDAO.getById(matricula);
    }

    public Aluno postAluno(Aluno aluno) {
        Curso curso = cursoService.getCurso(aluno.getCurso().getId());
        if (curso == null) {
            throw new IllegalArgumentException("Curso com ID " + aluno.getCurso().getId() + " não encontrado");
        }
        aluno.setCurso(curso);
        return alunoDAO.insert(aluno);
    }

    public Aluno putAluno(Aluno alunoAtualizado) {
        Aluno alunoExistente = alunoDAO.getById(alunoAtualizado.getMatricula());
        if (alunoExistente == null) {
            throw new IllegalArgumentException("Aluno com matrícula " + alunoAtualizado.getMatricula() + " não encontrado");
        }

        alunoExistente.setNome(alunoAtualizado.getNome());
        alunoExistente.setEmail(alunoAtualizado.getEmail());

        Curso novoCurso = cursoService.getCurso(alunoAtualizado.getCurso().getId());
        if (novoCurso == null) {
            throw new IllegalArgumentException("Curso com ID " + alunoAtualizado.getCurso().getId() + " não encontrado");
        }

        alunoExistente.setCurso(novoCurso);
        return alunoDAO.update(alunoExistente);
    }

    public void deleteAluno(long matricula) {
        Aluno aluno = alunoDAO.getById(matricula);
        if (aluno != null) {
            alunoDAO.delete(matricula);
        } else {
            throw new IllegalArgumentException("Aluno com matrícula " + matricula + " não encontrado");
        }
    }
}
