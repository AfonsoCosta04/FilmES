package com.filmees.backend.service;

import com.filmees.backend.model.Filme;
import com.filmees.backend.model.Funcionario;
import com.filmees.backend.model.Recomendacao;
import com.filmees.backend.model.RecomendacaoFilme;
import com.filmees.backend.repository.FilmeRepository;
import com.filmees.backend.repository.FuncionarioRepository;
import com.filmees.backend.repository.RecomendacaoFilmeRepository;
import com.filmees.backend.repository.RecomendacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    @Autowired
    private RecomendacaoFilmeRepository recomendacaoFilmeRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    public void guardarRecomendacao(Integer idFuncionario, List<Integer> filmes) {
        if (filmes == null || filmes.size() != 5) {
            throw new IllegalArgumentException("Tem de selecionar exatamente 5 filmes.");
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Recomendacao recomendacao = new Recomendacao();
        recomendacao.setFuncionario(funcionario);
        recomendacao = recomendacaoRepository.save(recomendacao);

        for (Integer idFilme : filmes) {
            Filme filme = filmeRepository.findById(idFilme)
                    .orElseThrow(() -> new RuntimeException("Filme não encontrado: " + idFilme));

            RecomendacaoFilme rf = new RecomendacaoFilme();
            rf.setRecomendacao(recomendacao);
            rf.setFilme(filme);
            recomendacaoFilmeRepository.save(rf);
        }
    }

    public List<Map<String, Object>> listarRecomendacoes() {
        List<Recomendacao> recomendacoes = recomendacaoRepository.findAll();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Recomendacao rec : recomendacoes) {
            Map<String, Object> linha = new HashMap<>();
            linha.put("nomeFuncionario", rec.getFuncionario().getNomeFuncionario());

            List<Map<String, Object>> filmes = rec.getRecomendacoesFilmes().stream().map(rf -> {
                Filme f = rf.getFilme();
                Map<String, Object> info = new HashMap<>();
                info.put("id", f.getIdFilme());
                info.put("nome", f.getTitulo());
                info.put("imagem", f.getFoto());
                return info;
            }).toList();

            linha.put("filmes", filmes);
            resultado.add(linha);
        }

        return resultado;
    }
}

