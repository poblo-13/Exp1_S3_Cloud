package com.duoc.enrollmentservice.exception;

import java.time.LocalDateTime;

//  Clase para representar la estructura de la respuesta de error
public record ErrorResponse(
        String error,
        String mensaje,
        int status,
        LocalDateTime timestamp
) {}