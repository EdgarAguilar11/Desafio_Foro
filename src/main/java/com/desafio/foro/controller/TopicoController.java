package com.desafio.foro.controller;

import com.desafio.foro.dto.DatosListadoTopico;
import com.desafio.foro.dto.DatosRespuestaTopico;
import com.desafio.foro.modelo.Topico;
import com.desafio.foro.modelo.obtener_datos.DatosActualizarTopico;
import com.desafio.foro.modelo.obtener_datos.DatosRegistroTopico;
import com.desafio.foro.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    TopicoRepository topicoRepository;

    @PostMapping
    @Tag(name = "Registro de Tópico", description = "Permite el registro de un tópico nuevo")
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos,
                                                                UriComponentsBuilder uriComponentsBuilder){
        Topico topico = topicoRepository.save(new Topico(datos));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus()?"Activo":"Desactivado",
                topico.getAutor(), topico.getCurso(), topico.getRespuesta());
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping("/{id}")
    @Tag(name = "Obtener Tópico por Id", description = "Permite obtener los detalles de un tópico por el Id")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        DatosRespuestaTopico datosTopico = new DatosRespuestaTopico(topico.getTitulo(),
                topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus()?"Activo":"Desactivado",
                topico.getAutor(), topico.getCurso(), topico.getRespuesta());
        return ResponseEntity.ok(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Tag(name = "Eliminar Tópico", description = "Permite eliminar un tópico por el ID")
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Tag(name = "Listar Tópicos", description = "Permite listar los tópicos registrados")
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopicos(@PageableDefault Pageable paginacion){
        return ResponseEntity.ok( topicoRepository.findByStatusTrue(paginacion)
                .map(DatosListadoTopico::new)
        );
    }

    @PutMapping
    @Transactional
    @Tag(name = "Actualizar Tópico", description = "Permite actualizar los tópicos actualizados")
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(
                new DatosRespuestaTopico(topico.getTitulo(),
                        topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus()?"Activo":"Desactivado",
                        topico.getAutor(), topico.getCurso(), topico.getRespuesta())
        );
    }
}
