package com.empresa.service;

import com.empresa.dao.CursoRepository;
import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }

    public Curso getCurso(long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso com ID " + id + " não encontrado"));
    }

    public Curso postCurso(Curso curso) {
        if (curso.getAlunos() != null) {
            for (Aluno aluno : curso.getAlunos()) {
                aluno.setCurso(curso);
            }
        }
        return cursoRepository.save(curso);
    }

    public Curso putCurso(Curso cursoAtualizado) {
        Curso cursoExistente = cursoRepository.findById(cursoAtualizado.getId())
                .orElseThrow(() -> new IllegalArgumentException("Curso com ID " + cursoAtualizado.getId() + " não encontrado"));

        cursoExistente.setNome(cursoAtualizado.getNome());

        if (cursoAtualizado.getAlunos() != null) {
            for (Aluno aluno : cursoAtualizado.getAlunos()) {
                aluno.setCurso(cursoExistente);
            }
        }
        cursoExistente.setAlunos(cursoAtualizado.getAlunos());

        return cursoRepository.save(cursoExistente);
    }

    public void deleteCurso(long idCurso) {
        Curso cursoToRemove = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("Curso com ID " + idCurso + " não encontrado"));

        if (cursoToRemove.getAlunos() != null) {
            for (Aluno aluno : cursoToRemove.getAlunos()) {
                aluno.setCurso(null);
            }
        }

        cursoRepository.delete(cursoToRemove);
    }
}
