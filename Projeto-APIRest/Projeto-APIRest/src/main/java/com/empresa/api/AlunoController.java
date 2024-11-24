package com.empresa.api;

import com.empresa.model.Aluno;
import com.empresa.service.AlunoService;
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
    public List<Aluno> getAllAlunos() {
        return alunoService.getAllAlunos();
    }

    @GetMapping(value = "/{matricula}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Aluno> getAluno(@PathVariable("matricula") long matricula) {
        Aluno aluno = alunoService.getAluno(matricula);
        if (aluno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> postAluno(@RequestBody Aluno aluno) {
        alunoService.postAluno(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aluno criado com sucesso");
    }

    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> putAluno(@RequestBody Aluno aluno) {
        alunoService.putAluno(aluno);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Aluno atualizado com sucesso");
    }

    @DeleteMapping(value = "/{matricula}")
    public ResponseEntity<String> deleteAluno(@PathVariable("matricula") long matricula) {
        alunoService.deleteAluno(matricula);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Aluno deletado com sucesso");
    }
}
