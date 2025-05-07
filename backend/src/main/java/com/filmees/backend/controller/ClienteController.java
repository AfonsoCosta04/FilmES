package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<?> listarClientes(HttpServletRequest request) {
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> obterClientePorEmail(@PathVariable String email, HttpServletRequest request) {
        // Apenas o próprio cliente ou admin pode ver os dados
        if (!SecurityUtil.isProprio(request, email) && !SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Optional<Cliente> cliente = clienteRepository.findByEmailCliente(email);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cliente.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Integer id,
                                              @RequestBody Cliente atualizado,
                                              HttpServletRequest request) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Apenas o próprio pode editar os seus dados
        if (!SecurityUtil.isProprio(request, clienteExistente.get().getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        atualizado.setIdCliente(id);
        Cliente salvo = clienteRepository.save(atualizado);
        return ResponseEntity.ok(salvo);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarCliente(@PathVariable Integer id,
                                           HttpServletRequest request) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Pode apagar se for o próprio ou o admin
        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente()) &&
                !SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        clienteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
