package com.duoc.enrollmentservice.service;

import com.duoc.enrollmentservice.model.GuiaDespacho;
import com.duoc.enrollmentservice.repository.GuiaDespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GuiaDespachoService {

    @Autowired
    private GuiaDespachoRepository repository;

    @Autowired
    private AwsService awsService;

    private final String BUCKET_NAME = "mi-nuevo-bucket-semana2";

    // Ruta temporal de Amazon EFS montada en la instancia EC2
    private final String EFS_PATH = "/mnt/efs/";

    public String procesarYSubirGuia(Long id, MultipartFile file) throws IOException {
        GuiaDespacho guia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guía no encontrada"));

        // 1 Guardar temporalmente en EFS
        File directorioEfs = new File(EFS_PATH);
        if (!directorioEfs.exists()) {
            directorioEfs.mkdirs(); // Crea la carpeta EFS si no existe
        }
        
        Path rutaTemporal = Paths.get(EFS_PATH + file.getOriginalFilename());
        Files.write(rutaTemporal, file.getBytes());
        guia.setEstado("GUARDADO_EN_EFS");
        repository.save(guia);

        // 2 Armar la ruta dinamica para S3 (/fecha/transportista/nombreArchivo)
        String añoMes = guia.getFecha().getYear() + String.format("%02d", guia.getFecha().getMonthValue());
        String rutaS3 = añoMes + "/" + guia.getTransportista() + "/" + file.getOriginalFilename();

        // 3 Subir a S3 usando tu metodo real
        awsService.uploadFile(
            BUCKET_NAME, 
            rutaS3, 
            file.getBytes(), 
            file.getContentType()
        );

        // 4 Actualizar metadata en Oracle
        guia.setRutaS3(rutaS3);
        guia.setEstado("SUBIDO_A_S3");
        repository.save(guia);

        // Opcional: Puedes descomentar la siguiente línea si quieres que el archivo 
        // se borre de la carpeta temporal EFS después de subir exitosamente a S3.
        // Files.deleteIfExists(rutaTemporal);

        return "Archivo guardado temporalmente en EFS y subido a AWS S3 en la ruta: " + rutaS3;
    }
}