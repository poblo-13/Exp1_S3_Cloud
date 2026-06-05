package com.duoc.enrollmentservice.controller;

import com.duoc.enrollmentservice.model.GuiaDespacho;
import com.duoc.enrollmentservice.repository.GuiaDespachoRepository;
import com.duoc.enrollmentservice.service.AwsService;
import com.duoc.enrollmentservice.service.GuiaDespachoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/guias")
public class GuiaDespachoController {

    @Autowired
    private GuiaDespachoRepository repository;

    @Autowired
    private GuiaDespachoService guiaService;

    @Autowired
    private AwsService awsService;

    // 1 Crear guias
    @PostMapping("/crear")
    public ResponseEntity<GuiaDespacho> crearGuia(@RequestBody GuiaDespacho guia) {
        guia.setEstado("CREADA");
        return ResponseEntity.ok(repository.save(guia));
    }

    // 2 Subir guias (pasa por EFS y luego a S3)
    @PostMapping("/{id}/upload")
    public ResponseEntity<String> subirGuia(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String resultado = guiaService.procesarYSubirGuia(id, file);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // 3 Descargar guias con validacion (token basico)
    @GetMapping("/{id}/download")
    public ResponseEntity<String> descargarGuia(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (!token.equals("Bearer mi-token-secreto")) {
            return ResponseEntity.status(401).body("Acceso denegado. Token inválido.");
        }
        // Llamada a tu AwsService
        return ResponseEntity.ok("Descarga iniciada para el ID " + id);
    }

    // 4 Modificar guias
    @PutMapping("/{id}/update")
    public ResponseEntity<String> actualizarGuia(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return subirGuia(id, file); // Reutilizamos el metodo de carga
    }

    // 5 Eliminar guias
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> eliminarGuia(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok("Guía eliminada de la base de datos");
    }

    // 6 Consultar por transportista y fecha
    @GetMapping("/buscar")
    public ResponseEntity<List<GuiaDespacho>> buscarGuias(@RequestParam String transportista, @RequestParam String fecha) {
        LocalDate fechaParseada = LocalDate.parse(fecha);
        return ResponseEntity.ok(repository.findByTransportistaAndFecha(transportista, fechaParseada));
    }
}