package com.duoc.courseservice.service;

import com.duoc.courseservice.exception.RecursoNoEncontradoException;
import com.duoc.courseservice.model.Curso;
import com.duoc.courseservice.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository repo;

    public CursoService(CursoRepository repo) {
        this.repo = repo;
    }

    public List<Curso> getAll() {
        return repo.findAll();
    }

    public Curso getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Curso no encontrado con id: " + id));
    }

    public Curso save(Curso c) {

        if (c.getNombre() == null || c.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }

        if (c.getProfesorNombre() == null || c.getProfesorNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del profesor es obligatorio");
        }

        if (c.getDuracionHoras() == null || c.getDuracionHoras() <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0");
        }

        if (c.getCosto() == null || c.getCosto() <= 0) {
            throw new IllegalArgumentException("El costo debe ser mayor a 0");
        }

        return repo.save(c);
    }

    public Curso update(Long id, Curso c) {

        Curso existente = getById(id);

        if (c.getNombre() != null && !c.getNombre().trim().isEmpty()) {
            existente.setNombre(c.getNombre());
        }

        if (c.getProfesorNombre() != null && !c.getProfesorNombre().trim().isEmpty()) {
            existente.setProfesorNombre(c.getProfesorNombre());
        }

        if (c.getDuracionHoras() != null && c.getDuracionHoras() > 0) {
            existente.setDuracionHoras(c.getDuracionHoras());
        }

        if (c.getCosto() != null && c.getCosto() > 0) {
            existente.setCosto(c.getCosto());
        }

        return repo.save(existente);
    }

    public void delete(Long id) {
        Curso existente = getById(id);
        repo.delete(existente);
    }
}