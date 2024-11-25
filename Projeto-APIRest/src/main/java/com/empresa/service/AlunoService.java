package com.empresa.service;

import com.empresa.dao.AlunoRepository;
import com.empresa.model.Aluno;
import com.empresa.model.Curso;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoService cursoService;

    public AlunoService(AlunoRepository alunoRepository, CursoService cursoService) {
        this.alunoRepository = alunoRepository;
        this.cursoService = cursoService;
    }

    public List<Aluno> getAllAlunos() {
        return alunoRepository.findAll();
    }

    public Aluno getAluno(long matricula) {
        return alunoRepository.findById(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Aluno com matrícula " + matricula + " não encontrado"));
    }

    public Aluno postAluno(Aluno aluno) {
        Curso curso = cursoService.getCurso(aluno.getCurso().getId());
        if (curso == null) {
            throw new IllegalArgumentException("Curso com ID " + aluno.getCurso().getId() + " não encontrado");
        }
        aluno.setCurso(curso);
        return alunoRepository.save(aluno);
    }

    public Aluno putAluno(Aluno alunoAtualizado) {
        Aluno alunoExistente = alunoRepository.findById(alunoAtualizado.getMatricula())
                .orElseThrow(() -> new IllegalArgumentException("Aluno com matrícula " + alunoAtualizado.getMatricula() + " não encontrado"));

        alunoExistente.setNome(alunoAtualizado.getNome());
        alunoExistente.setEmail(alunoAtualizado.getEmail());

        Curso novoCurso = cursoService.getCurso(alunoAtualizado.getCurso().getId());
        if (novoCurso == null) {
            throw new IllegalArgumentException("Curso com ID " + alunoAtualizado.getCurso().getId() + " não encontrado");
        }

        alunoExistente.setCurso(novoCurso);
        return alunoRepository.save(alunoExistente);
    }

    public void deleteAluno(long matricula) {
        if (alunoRepository.existsById(matricula)) {
            alunoRepository.deleteById(matricula);
        } else {
            throw new IllegalArgumentException("Aluno com matrícula " + matricula + " não encontrado");
        }
    }
}
