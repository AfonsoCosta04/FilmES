package com.filmees.backend.controller;

import com.filmees.backend.model.Admin;
import com.filmees.backend.model.Carrinho;
import com.filmees.backend.model.Cliente;
import com.filmees.backend.model.Funcionario;
import com.filmees.backend.repository.AdminRepository;
import com.filmees.backend.repository.CarrinhoRepository;
import com.filmees.backend.repository.ClienteRepository;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.security.JwtUtil;
import com.filmees.backend.security.LoginRateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LoginRateLimiterService rateLimiter;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest http) {
        String ip = http.getRemoteAddr();
        if (rateLimiter.isBlocked(ip)) {
            return ResponseEntity.status(429).body("Demasiadas tentativas. Tenta novamente mais tarde.");
        }

        Optional<Admin> admin = adminRepository.findByEmailAdmin(request.getEmail());
        if (admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPasswordAdmin())) {
            int tipoUtilizador = 1;
            String token = jwtUtil.generateToken(request.getEmail(), tipoUtilizador);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tipoUtilizador", tipoUtilizador);
            response.put("id", admin.get().getIdAdmin());
            response.put("nome", admin.get().getNomeAdmin());
            response.put("email", admin.get().getEmailAdmin());

            rateLimiter.reset(ip);

            return ResponseEntity.ok(response);
        }

        Optional<Funcionario> funcionario = funcionarioRepository.findByEmailFuncionario(request.getEmail());
        if (funcionario.isPresent() && passwordEncoder.matches(request.getPassword(), funcionario.get().getPasswordFuncionario())) {
            int tipoUtilizador = 2;
            String token = jwtUtil.generateToken(request.getEmail(), tipoUtilizador);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tipoUtilizador", tipoUtilizador);
            response.put("id", funcionario.get().getIdFuncionario());
            response.put("nome", funcionario.get().getNomeFuncionario());
            response.put("email", funcionario.get().getEmailFuncionario());

            rateLimiter.reset(ip);

            return ResponseEntity.ok(response);
        }

        Optional<Cliente> cliente = clienteRepository.findByEmailCliente(request.getEmail());
        if (cliente.isPresent() && passwordEncoder.matches(request.getPassword(), cliente.get().getPasswordCliente())) {
            int tipoUtilizador = 3;
            String token = jwtUtil.generateToken(request.getEmail(), tipoUtilizador);
            Carrinho carrinho = carrinhoRepository.findByIdCliente(cliente.get().getIdCliente()).orElse(null);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("id", cliente.get().getIdCliente());
            response.put("tipoUtilizador", tipoUtilizador);
            response.put("nome", cliente.get().getNomeCliente());
            response.put("email", cliente.get().getEmailCliente());

            if (carrinho != null) {
                response.put("idCarrinho", carrinho.getIdCarrinho());
            }

            rateLimiter.reset(ip);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
    }


    @PostMapping("/registo")
    public ResponseEntity<?> registarCliente(@RequestBody Cliente cliente) {
        // Verificações obrigatórias
        if (cliente.getNomeCliente() == null || cliente.getNomeCliente().isBlank() ||
                cliente.getEmailCliente() == null || cliente.getEmailCliente().isBlank() ||
                cliente.getPasswordCliente() == null || cliente.getPasswordCliente().isBlank() ||
                cliente.getNumeroDeTelefone() == null || cliente.getNumeroDeTelefone().isBlank() ||
                cliente.getDataDeNascCliente() == null) {
            return ResponseEntity.badRequest().body("Todos os campos obrigatórios devem ser preenchidos.");
        }

        // Verificar se o email já está em uso
        if (clienteRepository.findByEmailCliente(cliente.getEmailCliente()).isPresent()) {
            return ResponseEntity.status(409).body("Email já registado.");
        }

        // Verificar se o número de telefone já está em uso
        if (clienteRepository.existsByNumeroDeTelefone(cliente.getNumeroDeTelefone())) {
            return ResponseEntity.status(409).body("Número de telemóvel já registado.");
        }

        // Verificar se o NIF já está em uso (se for fornecido)
        if (cliente.getContribuinte() != null && clienteRepository.existsByContribuinte(cliente.getContribuinte())) {
            return ResponseEntity.status(409).body("NIF já registado.");
        }

        // Encriptar password e guardar
        cliente.setPasswordCliente(passwordEncoder.encode(cliente.getPasswordCliente()));
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }


    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
