package com.filmees.backend.controller;

import com.filmees.backend.model.Cliente;
import com.filmees.backend.model.Filme;
import com.filmees.backend.model.ListaDesejos;
import com.filmees.backend.repository.ClienteRepository;
import com.filmees.backend.repository.FilmeRepository;
import com.filmees.backend.repository.ListaDesejosRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lista-desejos")
public class ListaDesejosController {

    @Autowired
    private ListaDesejosRepository listaDesejosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> getWishlist(@PathVariable Integer idCliente, HttpServletRequest request) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        if (clienteOpt.isEmpty()) return ResponseEntity.notFound().build();

        Cliente cliente = clienteOpt.get();

        if (!SecurityUtil.isProprio(request, cliente.getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        List<ListaDesejos> desejos = listaDesejosRepository.findByCliente_IdCliente(cliente.getIdCliente());
        List<Filme> filmes = desejos.stream()
                .map(ListaDesejos::getFilme)
                .collect(Collectors.toList());
        return ResponseEntity.ok(filmes);
    }

    @PostMapping("/{idCliente}/{idFilme}")
    public ResponseEntity<?> adicionarDesejo(@PathVariable Integer idCliente, @PathVariable Integer idFilme) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
        Optional<Filme> filmeOpt = filmeRepository.findById(idFilme);

        if (clienteOpt.isEmpty() || filmeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente ou filme não encontrado.");
        }

        boolean jaExiste = listaDesejosRepository
                .findByCliente_IdClienteAndFilme_IdFilme(idCliente, idFilme)
                .isPresent();

        if (jaExiste) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este filme já está na lista de desejos.");
        }

        ListaDesejos novo = new ListaDesejos();
        novo.setCliente(clienteOpt.get());
        novo.setFilme(filmeOpt.get());

        listaDesejosRepository.save(novo);
        return ResponseEntity.ok("Filme adicionado à lista de desejos.");
    }

    @DeleteMapping("/{idCliente}/{idFilme}")
    public ResponseEntity<?> removerDesejo(@PathVariable Integer idCliente, @PathVariable Integer idFilme) {
        Optional<ListaDesejos> desejo = listaDesejosRepository.findByCliente_IdClienteAndFilme_IdFilme(idCliente, idFilme);

        if (desejo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filme não encontrado na lista de desejos.");
        }

        listaDesejosRepository.delete(desejo.get());
        return ResponseEntity.ok("Filme removido da lista de desejos.");
    }
}