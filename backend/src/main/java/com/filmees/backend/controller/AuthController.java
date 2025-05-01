package com.filmees.backend.controller;

import com.filmees.backend.model.Admin;
import com.filmees.backend.model.Cliente;
import com.filmees.backend.model.Funcionario;
import com.filmees.backend.repository.AdminRepository;
import com.filmees.backend.repository.ClienteRepository;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.security.JwtUtil;
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
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Admin> admin = adminRepository.findByEmailAdmin(request.getEmail());
        if (admin.isPresent() && passwordEncoder.matches(request.getPassword(), admin.get().getPasswordAdmin())) {
            int tipoUtilizador = 1;
            String token = jwtUtil.generateToken(request.getEmail(), tipoUtilizador);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tipoUtilizador", tipoUtilizador);
            response.put("nome", admin.get().getNomeAdmin());
            return ResponseEntity.ok(response);
        }

        Optional<Cliente> cliente = clienteRepository.findByEmailCliente(request.getEmail());
        if (cliente.isPresent() && passwordEncoder.matches(request.getPassword(), cliente.get().getPasswordCliente())) {
            int tipoUtilizador = 3;
            String token = jwtUtil.generateToken(request.getEmail(), tipoUtilizador);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tipoUtilizador", tipoUtilizador);
            response.put("nome", cliente.get().getNomeCliente());
            return ResponseEntity.ok(response);
        }

        Optional<Funcionario> funcionario = funcionarioRepository.findByEmailFuncionario(request.getEmail());
        if (funcionario.isPresent() && passwordEncoder.matches(request.getPassword(), funcionario.get().getPasswordFuncionario())) {
            int tipoUtilizador = 2;
            String token = jwtUtil.generateToken(request.getEmail(), tipoUtilizador);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tipoUtilizador", tipoUtilizador);
            response.put("nome", funcionario.get().getNomeFuncionario());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas"));
    }


    @PostMapping("/registo")
    public ResponseEntity<?> registarCliente(@RequestBody Cliente cliente) {
        if (clienteRepository.findByEmailCliente(cliente.getEmailCliente()).isPresent()) {
            return ResponseEntity.status(409).body("Email já registado.");
        }
        cliente.setPasswordCliente(passwordEncoder.encode(cliente.getPasswordCliente()));
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
