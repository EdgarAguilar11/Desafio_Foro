package com.desafio.foro.modelo.obtener_datos;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        String titulo,
        String mensaje,
        String curso,
        String respuesta
) {
}
