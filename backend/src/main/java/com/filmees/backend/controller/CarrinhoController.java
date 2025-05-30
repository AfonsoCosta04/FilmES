package com.filmees.backend.controller;

import com.filmees.backend.model.Carrinho;
import com.filmees.backend.model.Cliente;
import com.filmees.backend.repository.CarrinhoRepository;
import com.filmees.backend.repository.ClienteRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/carrinhos")
@CrossOrigin(origins = "http://localhost:3000")
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private static final Logger logger = LoggerFactory.getLogger(CarrinhoController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> obterCarrinho(@PathVariable Integer id, HttpServletRequest request) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(id);
        if (carrinho.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(carrinho.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(500).body("Cliente do carrinho não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        return ResponseEntity.ok(carrinho.get());
    }

    @PostMapping
    public ResponseEntity<?> adicionarCarrinho(@RequestBody Carrinho carrinho, HttpServletRequest request) {
        // Garante que o utilizador só pode criar carrinho para si
        Optional<Cliente> cliente = clienteRepository.findById(carrinho.getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(400).body("Cliente não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        return ResponseEntity.ok(carrinhoRepository.save(carrinho));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarCarrinho(@PathVariable Integer id,
                                               @RequestBody Carrinho carrinhoAtualizado,
                                               HttpServletRequest request) {
        Optional<Carrinho> existente = carrinhoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(existente.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(500).body("Cliente não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        carrinhoAtualizado.setIdCarrinho(id);
        return ResponseEntity.ok(carrinhoRepository.save(carrinhoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarCarrinho(@PathVariable Integer id, HttpServletRequest request) {
        Optional<Carrinho> existente = carrinhoRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(existente.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(500).body("Cliente não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        carrinhoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
