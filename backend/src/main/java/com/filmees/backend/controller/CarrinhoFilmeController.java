package com.filmees.backend.controller;

import com.filmees.backend.model.*;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrinho-filmes")
@CrossOrigin(origins = "http://localhost:3000")
public class CarrinhoFilmeController {

    @Autowired
    private CarrinhoFilmeRepository carrinhoFilmeRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/carrinho/{idCarrinho}/filmes")
    public ResponseEntity<?> listarFilmesPorCarrinho(@PathVariable Integer idCarrinho,
                                                     HttpServletRequest request) {

        String emailAutenticado = (String) request.getAttribute("emailAutenticado");

        Optional<Carrinho> carrinho = carrinhoRepository.findById(idCarrinho);
        if (carrinho.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(carrinho.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cliente do carrinho não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        List<Filme> filmes = carrinhoFilmeRepository.findAll().stream()
                .filter(cf -> cf.getCarrinho().getIdCarrinho().equals(idCarrinho))
                .map(CarrinhoFilme::getFilme)
                .toList();

        return ResponseEntity.ok(filmes);
    }

    @PostMapping("/carrinho/{idCarrinho}/filme/{idFilme}")
    public ResponseEntity<?> adicionarFilmeAoCarrinho(@PathVariable Integer idCarrinho,
                                                      @PathVariable Integer idFilme,
                                                      HttpServletRequest request) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(idCarrinho);
        if (carrinho.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(carrinho.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cliente não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Filme filme = filmeRepository.findById(idFilme)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        CarrinhoFilme novo = new CarrinhoFilme();
        novo.setCarrinho(carrinho.get());
        novo.setFilme(filme);

        return carrinhoFilmeRepository.save(novo);
    }

    @DeleteMapping("/carrinho/{idCarrinho}/filme/{idFilme}")
    public ResponseEntity<?> removerFilmeDoCarrinho(@PathVariable Integer idCarrinho,
                                                    @PathVariable Integer idFilme,
                                                    HttpServletRequest request) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(idCarrinho);
        if (carrinho.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(carrinho.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cliente não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        List<CarrinhoFilme> registos = carrinhoFilmeRepository.findAll().stream()
                .filter(cf -> cf.getCarrinho().getIdCarrinho().equals(idCarrinho) &&
                        cf.getFilme().getIdFilme().equals(idFilme))
                .collect(Collectors.toList());

        registos.forEach(carrinhoFilmeRepository::delete);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/carrinho/{idCarrinho}/total")
    public ResponseEntity<?> calcularTotalCarrinho(@PathVariable Integer idCarrinho,
                                                   HttpServletRequest request) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(idCarrinho);
        if (carrinho.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Cliente> cliente = clienteRepository.findById(carrinho.get().getIdCliente());
        if (cliente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cliente não encontrado.");
        }

        if (!SecurityUtil.isProprio(request, cliente.get().getEmailCliente())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<CarrinhoFilme> itens = carrinhoFilmeRepository.findAll().stream()
                .filter(cf -> cf.getCarrinho().getIdCarrinho().equals(idCarrinho))
                .toList();

        for (CarrinhoFilme cf : itens) {
            BigDecimal preco = BigDecimal.valueOf(cf.getFilme().getPreco());
            if (preco != null) {
                total = total.add(preco);
            }
        }

        return ResponseEntity.ok(total);
    }
}
