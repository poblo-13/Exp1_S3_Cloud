package com.duoc.enrollmentservice.repository;

import com.duoc.enrollmentservice.model.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Long> {
    // Consultar guias por transportista y fecha
    List<GuiaDespacho> findByTransportistaAndFecha(String transportista, LocalDate fecha);
}