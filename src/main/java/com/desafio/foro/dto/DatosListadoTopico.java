package com.desafio.foro.dto;

import com.desafio.foro.modelo.Topico;

public record DatosListadoTopico(String titulo, String autor, String curso) {
    public DatosListadoTopico(Topico topico){
        this(topico.getTitulo(), topico.getAutor(), topico.getCurso());
    }
}
