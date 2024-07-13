package com.desafio.foro.modelo.obtener_datos;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        String titulo,
        String mensaje,
        String autor,
        String curso,
        String respuesta
) {
}
