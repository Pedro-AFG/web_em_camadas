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

    public void postAluno(Aluno aluno) {
        Curso cursoCompleto = cursoService.getCurso(aluno.getCurso().getId());

        if (cursoCompleto != null) {
            aluno.setCurso(cursoCompleto);
            alunoDAO.insert(aluno);
        } else {
            throw new IllegalArgumentException("Curso com ID " + aluno.getCurso().getId() + " não encontrado");
        }
    }

    public void putAluno(Aluno alunoAtualizado) {
        Aluno alunoExistente = alunoDAO.getById(alunoAtualizado.getMatricula());

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
                alunoDAO.update(alunoExistente);
            } else {
                throw new IllegalArgumentException("Curso não encontrado");
            }
        } else {
            throw new IllegalArgumentException("Aluno não encontrado");
        }
    }

    public void deleteAluno(long matricula) {
        Aluno alunoToRemove = alunoDAO.getById(matricula);
        if (alunoToRemove != null) {
            alunoDAO.delete(matricula);

            Curso curso = alunoToRemove.getCurso();
            if (curso != null) {
                curso.getAlunos().remove(alunoToRemove);
            }
        }
    }
}
