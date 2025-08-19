package com.forohub.forohub.controller;

import com.forohub.forohub.model.Topico;
import com.forohub.forohub.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    // Crear nuevo tópico
    @PostMapping
    public String crearTopico(@RequestBody @Valid Topico topico) {
        if (topicoRepository.existsByTituloAndMensaje(topico.getTitulo(), topico.getMensaje())) {
            return "Ese tópico ya existe";
        }
        topicoRepository.save(topico);
        return "Tópico creado con éxito";
    }

    // Listar todos
    @GetMapping
    public List<Topico> listarTopicos() {
        return topicoRepository.findAll();
    }

    // Ver detalle por id
    @GetMapping("/{id}")
    public Topico verTopico(@PathVariable Long id) {
        return topicoRepository.findById(id).orElse(null);
    }

    // Actualizar
    @PutMapping("/{id}")
    public String actualizarTopico(@PathVariable Long id, @RequestBody Topico nuevo) {
        return topicoRepository.findById(id).map(topico -> {
            topico.setTitulo(nuevo.getTitulo());
            topico.setMensaje(nuevo.getMensaje());
            topico.setAutor(nuevo.getAutor());
            topico.setCurso(nuevo.getCurso());
            topicoRepository.save(topico);
            return "Tópico actualizado";
        }).orElse("Tópico no encontrado");
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public String borrarTopico(@PathVariable Long id) {
        if (topicoRepository.existsById(id)) {
            topicoRepository.deleteById(id);
            return "Tópico eliminado";
        }
        return "Tópico no encontrado";
    }
}
