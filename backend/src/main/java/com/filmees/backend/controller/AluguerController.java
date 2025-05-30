package com.filmees.backend.controller;

import com.filmees.backend.model.Aluguer;
import com.filmees.backend.model.Carrinho;
import com.filmees.backend.model.Cliente;
import com.filmees.backend.model.Filme;
import com.filmees.backend.repository.*;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private static final Logger logger = LoggerFactory.getLogger(AluguerController.class);

    @GetMapping
    public ResponseEntity<?> listarTodos(HttpServletRequest request) {
        logger.debug("Requisição para listar todos os alugueres.");
        if (!SecurityUtil.isFuncionario(request) && !SecurityUtil.isAdmin(request)) {
            logger.warn("Acesso negado para listar alugueres.");
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        logger.info("Alugueres ativos listados com sucesso.");
        return ResponseEntity.ok(aluguerRepository.findByEstadoNot("devolvido"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguer> obterPorId(@PathVariable Integer id) {
        logger.debug("Requisição para obter aluguer com ID: {}", id);
        Optional<Aluguer> aluguer = aluguerRepository.findById(id);
        return aluguer.map(ResponseEntity::ok).orElseGet(() -> {
            logger.warn("Aluguer com ID {} não encontrado.", id);
            return ResponseEntity.notFound().build();
        });
    }

    @Transactional
    @PostMapping
    public ResponseEntity<?> criarAluguer(@RequestBody Aluguer novoAluguer, HttpServletRequest request) {
        logger.debug("Iniciando criação de novo aluguer: {}", novoAluguer);
        if (!SecurityUtil.isCliente(request)) {
            logger.warn("Tentativa de criar aluguer sem ser cliente.");
            return ResponseEntity.status(403).body("Apenas clientes autenticados podem alugar filmes.");
        }

        Integer clienteId = SecurityUtil.getUserId(request);
        logger.debug("ID do cliente autenticado: {}", clienteId);
        if (clienteId == null) {
            logger.warn("Token inválido ou expirado.");
            return ResponseEntity.status(401)
                    .body("Token inválido ou expirado.");
        }
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            logger.warn("Cliente com ID {} não encontrado.", clienteId);
            return ResponseEntity.badRequest().body("Cliente não encontrado.");
        }

        if (novoAluguer.getFilmes() == null || novoAluguer.getFilmes().isEmpty()) {
            logger.warn("Aluguer sem filmes.");
            return ResponseEntity.badRequest().body("É necessário pelo menos um filme.");
        }

        LocalDate dataLev = novoAluguer.getDataLevantamento();
        LocalDate dataDev = novoAluguer.getDataDevolucao();
        logger.debug("Data levantamento: {}, Data devolução: {}", dataLev, dataDev);
        if (dataLev == null || dataDev == null) {
            return ResponseEntity.badRequest().body("Datas de levantamento e devolução são obrigatórias.");
        }
        if (dataDev.isBefore(dataLev)) {
            return ResponseEntity.badRequest().body("Data de devolução não pode ser anterior à de levantamento.");
        }
        long dias = ChronoUnit.DAYS.between(dataLev, dataDev);
        if (dias > 10) {
            logger.warn("Tentativa de aluguel com mais de 10 dias: {}", dias);
            return ResponseEntity.badRequest().body("O prazo máximo de aluguel é de 10 dias.");
        }

        // Carregar filmes por ID (para evitar objetos incompletos)
        List<Integer> idsFilmes = novoAluguer.getFilmes().stream()
                .map(Filme::getIdFilme)
                .toList();
        logger.debug("IDs dos filmes a alugar: {}", idsFilmes);

        long distintos = idsFilmes.stream().distinct().count();
        if (distintos != idsFilmes.size()) {
            return ResponseEntity.badRequest()
                    .body("Não podes pedir o mesmo filme duas vezes no mesmo aluguer.");
        }

        List<Filme> filmesValidos = filmeRepository.findAllById(idsFilmes);

        if (filmesValidos.size() != idsFilmes.size()) {
            return ResponseEntity.badRequest()
                    .body("Um ou mais filmes inválidos.");
        }


        var estadosAtivos = List.of("reservado", "alugado");
        List<Aluguer> alugueresAtivos = aluguerRepository
                .findByCliente_IdClienteAndEstadoIn(clienteId, estadosAtivos);

        Set<Integer> jaAlugados = alugueresAtivos.stream()
                .flatMap(a -> a.getFilmes().stream())
                .map(Filme::getIdFilme)
                .collect(Collectors.toSet());

        for (Integer idFilme : idsFilmes) {
            if (jaAlugados.contains(idFilme)) {
                return ResponseEntity.badRequest()
                        .body("O filme de id " + idFilme + " já está reservado/alugado.");
            }
        }

        LocalDate dataNascimento = clienteOpt.get().getDataDeNascCliente();
        if (dataNascimento == null) {
            return ResponseEntity.badRequest().body("Data de nascimento do cliente não está definida.");
        }
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        logger.debug("Idade do cliente: {}", idade);

        for (Filme filme : filmesValidos) {
            Integer classificacao = filme.getIdadeRecomendada();
            if (classificacao != null && idade < classificacao) {
                return ResponseEntity.badRequest()
                        .body("O cliente não tem idade suficiente para alugar o filme: " + filme.getTitulo());
            }
        }

        int totalAtuais = jaAlugados.size();
        int totalNovos = filmesValidos.size();
        if (totalAtuais + totalNovos > 5) {
            return ResponseEntity.badRequest()
                    .body("Limite de 5 filmes simultâneos excedido.");
        }

        novoAluguer.setCliente(clienteOpt.get());
        novoAluguer.setFilmes(filmesValidos);
        novoAluguer.setEstado("reservado");
        Carrinho carrinho = carrinhoRepository.findByIdCliente(clienteOpt.get().getIdCliente())
                .orElse(null);

        if (carrinho != null) {
            carrinhoFilmeRepository.deleteByCarrinhoIdCarrinho(carrinho.getIdCarrinho());
            logger.debug("Carrinho do cliente limpo após criação do aluguer.");
        }

        Aluguer aluguerGravado = aluguerRepository.save(novoAluguer);
        logger.info("Novo aluguer criado: {}", aluguerGravado.getIdAluguer());
        return ResponseEntity.ok(aluguerGravado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarAluguer(@PathVariable Integer id, HttpServletRequest request) {
        logger.debug("Requisição para deletar aluguer {}", id);
        Optional<Aluguer> aluguer = aluguerRepository.findById(id);

        if (aluguer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean isProprio = SecurityUtil.isProprio(request, aluguer.get().getCliente().getEmailCliente());
        boolean isFuncionarioOuAdmin = SecurityUtil.isFuncionario(request) || SecurityUtil.isAdmin(request);

        if (!isProprio && !isFuncionarioOuAdmin) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        if (!aluguer.get().getEstado().equals("reservado")) {
            logger.warn("Aluguer {} não encontrado para exclusão.", id);
            return ResponseEntity.status(403).body("Aluguer já confirmado");
        }
        aluguerRepository.deleteById(id);
        logger.info("Aluguer {} excluído com sucesso.", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cliente/tudo/{id}")
    public ResponseEntity<?> listarPorClienteTudo(
            @PathVariable Integer id,
            HttpServletRequest request) {
        logger.debug("Requisição para listar todos os alugueres do cliente com id {}", id);
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean isProprio = SecurityUtil.isProprio(request, clienteOpt.get().getEmailCliente());
        if (!isProprio) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        List<Aluguer> alugueres = aluguerRepository.findByCliente_IdCliente(clienteOpt.get().getIdCliente());
        logger.info("Alugueres do cliente com id {} listados com sucesso.", id);
        return ResponseEntity.ok(alugueres);
    }

    @GetMapping("/cliente/alugados/{id}")
    public ResponseEntity<?> listarPorClienteAlugado(
            @PathVariable Integer id,
            HttpServletRequest request) {
        logger.debug("Requisição para listar todos os alugueres já levantados do cliente com id {}", id);
        Optional<Cliente> clienteOpt = clienteRepository.findById(id);
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // só o próprio, funcionário ou admin podem ver
        boolean isProprio = SecurityUtil.isProprio(request, clienteOpt.get().getEmailCliente());
        boolean isFuncionarioOuAdmin = SecurityUtil.isFuncionario(request) || SecurityUtil.isAdmin(request);
        if (!isProprio && !isFuncionarioOuAdmin) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        // busca apenas os alugueres desse cliente que estão em "alugado"
        List<Aluguer> alugueresAlugados = aluguerRepository
                .findByCliente_IdClienteAndEstado(id, "alugado");

        logger.info("Alugueres do cliente com estado alugado e id {} listados com sucesso.", id);
        return ResponseEntity.ok(alugueresAlugados);
    }

    @GetMapping("/reservados")
    public ResponseEntity<?> listarReservados(HttpServletRequest request) {
        logger.debug("Requisição para listar todas as reservas");
        if (!SecurityUtil.isFuncionario(request) && !SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }

        List<Aluguer> reservados = aluguerRepository.findByEstadoComCliente("reservado");
        logger.info("Reservas listadas com sucesso.");
        return ResponseEntity.ok(reservados);
    }

    @PutMapping("/{id}/alugar")
    public ResponseEntity<?> marcarComoAlugado(@PathVariable Integer id, HttpServletRequest req) {
        logger.debug("Funcionario/Admin está marcando aluguer {} como 'alugado'", id);
        if (!SecurityUtil.isFuncionario(req) && !SecurityUtil.isAdmin(req)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<Aluguer> opt = aluguerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Aluguer a = opt.get();
        a.setEstado("alugado");
        aluguerRepository.save(a);
        logger.info("Aluguer {} marcado como 'alugado'", id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> marcarComoDecolvido(@PathVariable Integer id, HttpServletRequest req) {
        logger.debug("Funcionario/Admin está marcando aluguer {} como 'devolvido'", id);
        if (!SecurityUtil.isFuncionario(req) && !SecurityUtil.isAdmin(req)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        Optional<Aluguer> opt = aluguerRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        Aluguer a = opt.get();
        a.setEstado("devolvido");
        aluguerRepository.save(a);
        logger.info("Aluguer {} marcado como 'devolvido'", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<?> pesquisarPorNomeCliente(@RequestParam String nome, HttpServletRequest request) {
        logger.debug("Funcionario/Admin está a pesquisar os aluguer de {}", nome);
        if (!SecurityUtil.isFuncionario(request) && !SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(403).body("Acesso negado.");
        }
        logger.info("Aluguers de {} pesquisados com sucesso", nome);
        return ResponseEntity.ok(aluguerRepository.findByCliente_NomeClienteContainingIgnoreCaseAndEstadoNot(nome, "DEVOLVIDO"));
    }
}
