package com.duoc.courseservice.model;

import jakarta.persistence.*;
import lombok.*;

// Entidad que representa un curso en el sistema.
@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String profesorNombre;
    private Integer duracionHoras;
    private Integer costo; 
}