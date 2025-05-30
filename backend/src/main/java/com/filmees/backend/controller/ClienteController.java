package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);


    @GetMapping
    public ResponseEntity<?> listarClientes(HttpServletRequest request) {
        logger.info("Listar todos os clientes");
        if (!SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> obterClientePorEmail(@PathVariable String email, HttpServletRequest request) {
        logger.info("Obter cliente com email: {}", email);
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
        logger.info("Atualizar dados do cliente com id {}", id);
        Optional<Cliente> clienteExistente = clienteRepository.findById(id);
        if (clienteExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Apenas o próprio pode editar os seus dados
        if (!SecurityUtil.isProprio(request, clienteExistente.get().getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        atualizado.setIdCliente(id);
        if (atualizado.getNomeCliente() == null) atualizado.setNomeCliente(clienteExistente.get().getNomeCliente());
        if (atualizado.getEmailCliente() == null) atualizado.setEmailCliente(clienteExistente.get().getEmailCliente());
        if (atualizado.getDataDeNascCliente() == null)
            atualizado.setDataDeNascCliente(clienteExistente.get().getDataDeNascCliente());
        if (atualizado.getNumeroDeTelefone() == null)
            atualizado.setNumeroDeTelefone(clienteExistente.get().getNumeroDeTelefone());
        if (atualizado.getContribuinte() == null) atualizado.setContribuinte(clienteExistente.get().getContribuinte());
        atualizado.setPasswordCliente(clienteExistente.get().getPasswordCliente());
        Cliente salvo = clienteRepository.save(atualizado);
        logger.info("Cliente com id {} atualizado com sucesso", id);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> atualizarPassword(@PathVariable Integer id,
                                               @RequestBody Map<String, String> body,
                                               HttpServletRequest request) {
        logger.info("Atualizar password do cliente com id {}", id);
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cliente cliente = clienteOpt.get();

        if (!SecurityUtil.isProprio(request, cliente.getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        String atual = body.get("passwordAtual");
        String nova = body.get("novaPassword");

        if (atual == null || nova == null) {
            return ResponseEntity.badRequest().body("Campos obrigatórios em falta.");
        }

        if (!passwordEncoder.matches(atual, cliente.getPasswordCliente())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password atual incorreta.");
        }

        cliente.setPasswordCliente(passwordEncoder.encode(nova));
        clienteRepository.save(cliente);

        logger.info("Password do cliente {} atualizada com sucesso", id);
        return ResponseEntity.ok("Password atualizada com sucesso.");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> apagarCliente(@PathVariable Integer id,
                                           HttpServletRequest request) {
        logger.info("Apagar cliente com id {}", id);
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
        logger.info("Cliente com id {} apagado com sucesso", id);
        return ResponseEntity.ok().build();
    }


}
