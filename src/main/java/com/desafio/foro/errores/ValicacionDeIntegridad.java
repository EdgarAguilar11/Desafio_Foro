package com.desafio.foro.errores;

public class ValicacionDeIntegridad extends RuntimeException {
    public ValicacionDeIntegridad(String s) {
        super(s);
    }
}
