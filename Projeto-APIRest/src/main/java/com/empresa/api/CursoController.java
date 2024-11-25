package com.empresa.api;

import com.empresa.model.Curso;
import com.empresa.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Curso>> getAllCursos() {
        List<Curso> cursos = cursoService.getAllCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Curso> getCurso(@PathVariable("id") long id) {
        Curso curso = cursoService.getCurso(id);
        return ResponseEntity.ok(curso); // Exceções serão tratadas globalmente
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<Curso> postCurso(@Valid @RequestBody Curso curso) {
        Curso cursoCriado = cursoService.postCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCriado);
    }

    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<Curso> putCurso(@Valid @RequestBody Curso curso) {
        Curso cursoAtualizado = cursoService.putCurso(curso);
        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable("id") long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}