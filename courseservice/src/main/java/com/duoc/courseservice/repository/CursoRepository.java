package com.duoc.courseservice.repository;

import com.duoc.courseservice.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

/// Repositorio encargado de las operaciones de persistencia
public interface CursoRepository extends JpaRepository<Curso, Long> {
}