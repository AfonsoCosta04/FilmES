package com.filmees.backend.controller;

import com.filmees.backend.model.TipoUtilizador;
import com.filmees.backend.repository.TipoUtilizadorRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipos-utilizador")
@CrossOrigin(origins = "http://localhost:3000")
public class TipoUtilizadorController {

    @Autowired
    private TipoUtilizadorRepository tipoUtilizadorRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(tipoUtilizadorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<TipoUtilizador> tipo = tipoUtilizadorRepository.findById(id);
        return tipo.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody TipoUtilizador tipoUtilizador, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(tipoUtilizadorRepository.save(tipoUtilizador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id,
                                       @RequestBody TipoUtilizador atualizado,
                                       HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        atualizado.setIdTipo(id);
        return ResponseEntity.ok(tipoUtilizadorRepository.save(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagar(@PathVariable Integer id, HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        tipoUtilizadorRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
