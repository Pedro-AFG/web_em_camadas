package com.empresa.api;

import com.empresa.model.Curso;
import com.empresa.service.CursoService;
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
    public List<Curso> getAllCursos() {
        return cursoService.getAllCursos();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Curso> getCurso(@PathVariable("id") long id) {
        Curso curso = cursoService.getCurso(id);
        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> postCurso(@RequestBody Curso curso) {
        cursoService.postCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body("Curso criado com sucesso");
    }

    @PutMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> putCurso(@RequestBody Curso curso) {
        cursoService.putCurso(curso);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Curso atualizado com sucesso");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCurso(@PathVariable("id") long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Curso deletado com sucesso");
    }
}
