package com.filmees.backend.controller;

import com.filmees.backend.model.Aluguer;
import com.filmees.backend.model.Carrinho;
import com.filmees.backend.model.Cliente;
import com.filmees.backend.model.Filme;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CarrinhoFilmeRepository carrinhoFilmeRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

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

    @Transactional
    @PostMapping
    public ResponseEntity<?> criarAluguer(@RequestBody Aluguer novoAluguer, HttpServletRequest request) {
        if (!SecurityUtil.isCliente(request)) {
            return ResponseEntity.status(403).body("Apenas clientes autenticados podem alugar filmes.");
        }

        if (novoAluguer.getCliente() == null || novoAluguer.getCliente().getIdCliente() == null) {
            return ResponseEntity.badRequest().body("Cliente inválido.");
        }

        if (novoAluguer.getFilmes() == null || novoAluguer.getFilmes().isEmpty()) {
            return ResponseEntity.badRequest().body("É necessário pelo menos um filme.");
        }

        // Validar cliente
        Optional<Cliente> clienteOpt = clienteRepository.findById(novoAluguer.getCliente().getIdCliente());
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente não encontrado.");
        }

        // Carregar filmes por ID (para evitar objetos incompletos)
        List<Integer> idsFilmes = novoAluguer.getFilmes().stream()
                .map(Filme::getIdFilme)
                .toList();

        List<Filme> filmesValidos = filmeRepository.findAllById(idsFilmes);

        if (filmesValidos.isEmpty()) {
            return ResponseEntity.badRequest().body("Filmes inválidos.");
        }

        novoAluguer.setCliente(clienteOpt.get());
        novoAluguer.setFilmes(filmesValidos);
        novoAluguer.setEstado("reservado");
        Carrinho carrinho = carrinhoRepository.findByIdCliente(clienteOpt.get().getIdCliente())
                .orElse(null);

        if (carrinho != null) {
            carrinhoFilmeRepository.deleteByCarrinhoIdCarrinho(carrinho.getIdCarrinho());
        }

        Aluguer aluguerGravado = aluguerRepository.save(novoAluguer);
        return ResponseEntity.ok(aluguerGravado);
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
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean isProprio = SecurityUtil.isProprio(request, cliente.get().getEmailCliente());
        boolean isFuncionarioOuAdmin = SecurityUtil.isFuncionario(request) || SecurityUtil.isAdmin(request);

        if (!isProprio && !isFuncionarioOuAdmin) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        List<Aluguer> alugueres = aluguerRepository.findByCliente_IdCliente(id);
        return ResponseEntity.ok(alugueres);
    }

}
