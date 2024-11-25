package com.empresa.api;

import com.empresa.model.Aluno;
import com.empresa.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Aluno>> getAllAlunos() {
        List<Aluno> alunos = alunoService.getAllAlunos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping(value = "/{matricula}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Aluno> getAluno(@PathVariable("matricula") long matricula) {
        Aluno aluno = alunoService.getAluno(matricula);
        return ResponseEntity.ok(aluno);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<Aluno> postAluno(@RequestBody Aluno aluno) {
        Aluno alunoCriado = alunoService.postAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoCriado);
    }

    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<Aluno> putAluno(@RequestBody Aluno aluno) {
        Aluno alunoAtualizado = alunoService.putAluno(aluno);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping(value = "/{matricula}")
    public ResponseEntity<Void> deleteAluno(@PathVariable("matricula") long matricula) {
        alunoService.deleteAluno(matricula);
        return ResponseEntity.noContent().build();
    }
}