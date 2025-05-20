package com.filmees.backend.controller;

import com.filmees.backend.model.Filme;
import com.filmees.backend.model.Funcionario;
import com.filmees.backend.model.Recomendacao;
import com.filmees.backend.model.RecomendacaoFilme;
import com.filmees.backend.repository.FilmeRepository;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.repository.RecomendacaoFilmeRepository;
import com.filmees.backend.repository.RecomendacaoRepository;
import com.filmees.backend.security.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/recomendacoes")
@CrossOrigin(origins = "http://localhost:3000")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    @Autowired
    private RecomendacaoFilmeRepository recomendacaoFilmeRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @PostMapping("/recomendarfilmes")
    public ResponseEntity<?> recomendarFilmes(
            @RequestBody Map<String, Object> payload,
            HttpServletRequest request) {

        if (!SecurityUtil.isFuncionario(request) && !SecurityUtil.isAdmin(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado.");
        }

        Integer idFuncionario = Integer.parseInt(payload.get("idFuncionario").toString());
        List<Integer> filmesIds = (List<Integer>) payload.get("filmes");

        if (filmesIds == null || filmesIds.size() != 5) {
            return ResponseEntity.badRequest().body("Tens de selecionar exatamente 5 filmes.");
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElse(null);
        if (funcionario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        }

        // Apagar recomendação anterior (se existir)
        Optional<Recomendacao> antiga = recomendacaoRepository.findByFuncionario(funcionario);
        antiga.ifPresent(recomendacaoExistente -> {
            recomendacaoFilmeRepository.deleteByRecomendacao(recomendacaoExistente);
            recomendacaoRepository.delete(recomendacaoExistente);
        });

        // Criar nova recomendação (sem data)
        Recomendacao nova = new Recomendacao();
        nova.setFuncionario(funcionario);
        recomendacaoRepository.save(nova);

        // Associar os 5 filmes
        for (Integer idFilme : filmesIds) {
            Filme filme = filmeRepository.findById(idFilme).orElse(null);
            if (filme == null) continue;

            RecomendacaoFilme rf = new RecomendacaoFilme();
            rf.setRecomendacao(nova);
            rf.setFilme(filme);
            recomendacaoFilmeRepository.save(rf);
        }

        return ResponseEntity.ok("Recomendação guardada com sucesso!");
    }
}
