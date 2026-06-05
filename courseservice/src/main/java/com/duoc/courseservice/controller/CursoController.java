package com.duoc.courseservice.controller;

import com.duoc.courseservice.model.Curso;
import com.duoc.courseservice.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// Controlador encargado de manejar las solicitudes HTTP relacionadas con la entidad Curso.
@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Curso> save(@RequestBody Curso c) {
        return ResponseEntity.status(201).body(service.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable Long id, @RequestBody Curso c) {
        return ResponseEntity.ok(service.update(id, c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}