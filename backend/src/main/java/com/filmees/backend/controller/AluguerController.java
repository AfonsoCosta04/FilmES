package com.filmees.backend.controller;

import com.filmees.backend.model.Aluguer;
import com.filmees.backend.model.Cliente;
import com.filmees.backend.repository.AluguerRepository;
import com.filmees.backend.repository.ClienteRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alugueres")
@CrossOrigin(origins = "http://localhost:3000")
public class AluguerController {

    @Autowired
    private AluguerRepository aluguerRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<?> listarTodos(HttpServletRequest request) {
        if (!SecurityUtil.isFuncionario(request) && !SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(aluguerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguer> obterPorId(@PathVariable Integer id) {
        Optional<Aluguer> aluguer = aluguerRepository.findById(id);
        return aluguer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criarAluguer(@RequestBody Aluguer novoAluguer, HttpServletRequest request) {
        if (!SecurityUtil.isCliente(request)) {
            return ResponseEntity.status(403).body("Apenas clientes autenticados podem alugar filmes.");
        }
        return ResponseEntity.ok(aluguerRepository.save(novoAluguer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAluguer(@PathVariable Integer id, HttpServletRequest request) {
        Optional<Aluguer> aluguer = aluguerRepository.findById(id);

        if (aluguer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean isProprio = SecurityUtil.isProprio(request, aluguer.get().getCliente().getEmailCliente());
        boolean isFuncionarioOuAdmin = SecurityUtil.isFuncionario(request) || SecurityUtil.isAdmin(request);

        if (!isProprio && !isFuncionarioOuAdmin) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        aluguerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Integer id, HttpServletRequest request) {
        // Verificar se é o próprio cliente OU funcionário/admin
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean isProprio = SecurityUtil.isProprio(request, cliente.get().getEmailCliente());
        boolean isFuncionarioOuAdmin = SecurityUtil.isFuncionario(request) || SecurityUtil.isAdmin(request);

        if (!isProprio && !isFuncionarioOuAdmin) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        // Retornar os alugueres filtrados
        List<Aluguer> alugueresDoCliente = aluguerRepository.findAll().stream()
                .filter(a -> a.getCliente().getIdCliente().equals(id))
                .toList();

        return ResponseEntity.ok(alugueresDoCliente);
    }

}
