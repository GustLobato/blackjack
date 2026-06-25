package com.faculdade.blackjack.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.faculdade.blackjack.dto.EstadoJogo;
import com.faculdade.blackjack.dto.RodadaDTO;
import com.faculdade.blackjack.entity.ResultadoRodada;
import com.faculdade.blackjack.model.Carta;
import com.faculdade.blackjack.model.Jogo;
import com.faculdade.blackjack.repository.ResultadoRodadaRepository;

// Aqui ficam as regras do jogo. O Spring cria UMA instancia (singleton).
// A partida atual fica em memoria; o historico de rodadas vai para o banco.
@Service
public class JogoService {

    // O Spring injeta o repositorio pelo construtor (injecao de dependencia)
    private final ResultadoRodadaRepository historicoRepository;

    private Jogo jogo; // partida atual (null = nenhuma partida iniciada ainda)

    public JogoService(ResultadoRodadaRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    // POST /jogo/novo -> comeca uma partida nova
    public EstadoJogo novoJogo() {
        jogo = new Jogo();
        // distribui 2 cartas para cada um, alternando (jogador, dealer, jogador, dealer)
        jogo.getJogador().receber(jogo.getBaralho().comprar());
        jogo.getDealer().receber(jogo.getBaralho().comprar());
        jogo.getJogador().receber(jogo.getBaralho().comprar());
        jogo.getDealer().receber(jogo.getBaralho().comprar());
        jogo.setStatus("EM_ANDAMENTO");
        return montarEstado();
    }

    // POST /jogo/comprar -> jogador puxa mais uma carta
    public EstadoJogo comprar() {
        if (!emAndamento()) {
            return montarEstado();
        }
        jogo.getJogador().receber(jogo.getBaralho().comprar());
        if (jogo.getJogador().estourou()) {
            jogo.setStatus("JOGADOR_ESTOUROU");
            registrarResultado();
        }
        return montarEstado();
    }

    // POST /jogo/parar -> jogador para e o dealer joga sozinho (compra ate 17+)
    public EstadoJogo parar() {
        if (!emAndamento()) {
            return montarEstado();
        }
        while (jogo.getDealer().pontuacao() < 17) {
            jogo.getDealer().receber(jogo.getBaralho().comprar());
        }
        decidirVencedor();
        registrarResultado();
        return montarEstado();
    }

    // GET /jogo/estado -> apenas devolve o estado atual
    public EstadoJogo estadoAtual() {
        return montarEstado();
    }

    // DELETE /jogo/historico -> apaga todas as rodadas do banco
    public void limparHistorico() {
        historicoRepository.deleteAll();
    }

    private boolean emAndamento() {
        return jogo != null && "EM_ANDAMENTO".equals(jogo.getStatus());
    }

    // Compara as pontuacoes depois que o dealer terminou de jogar
    private void decidirVencedor() {
        int pontosJogador = jogo.getJogador().pontuacao();
        int pontosDealer = jogo.getDealer().pontuacao();

        if (pontosDealer > 21) {
            jogo.setStatus("DEALER_ESTOUROU");
        } else if (pontosJogador > pontosDealer) {
            jogo.setStatus("JOGADOR_VENCEU");
        } else if (pontosDealer > pontosJogador) {
            jogo.setStatus("DEALER_VENCEU");
        } else {
            jogo.setStatus("EMPATE");
        }
    }

    // Salva a rodada terminada no banco (atraves do repositorio)
    private void registrarResultado() {
        ResultadoRodada rodada = new ResultadoRodada(
                resultadoDoJogador(jogo.getStatus()),
                jogo.getJogador().pontuacao(),
                jogo.getDealer().pontuacao());
        historicoRepository.save(rodada);
    }

    // Monta a resposta enviada ao front-end
    private EstadoJogo montarEstado() {
        EstadoJogo estado = new EstadoJogo();
        estado.setHistorico(buscarHistorico());

        // Nenhuma partida iniciada ainda
        if (jogo == null) {
            estado.setStatus("SEM_JOGO");
            estado.setCartasJogador(new ArrayList<>());
            estado.setCartasDealer(new ArrayList<>());
            estado.setMensagem("");
            return estado;
        }

        estado.setStatus(jogo.getStatus());
        estado.setMensagem(mensagemPara(jogo.getStatus()));

        // O jogador ve sempre todas as proprias cartas
        estado.setCartasJogador(jogo.getJogador().getCartas());
        estado.setPontuacaoJogador(jogo.getJogador().pontuacao());

        if (emAndamento()) {
            // Durante a partida a 2a carta do dealer fica virada para baixo
            estado.setCartasDealer(cartasDealerComCartaEscondida());
            estado.setPontuacaoDealer(pontosVisiveisDoDealer());
        } else {
            // Rodada terminada: revela tudo do dealer
            estado.setCartasDealer(jogo.getDealer().getCartas());
            estado.setPontuacaoDealer(jogo.getDealer().pontuacao());
        }

        return estado;
    }

    // Busca as ultimas rodadas no banco e converte cada entidade em DTO
    private List<RodadaDTO> buscarHistorico() {
        List<RodadaDTO> lista = new ArrayList<>();
        for (ResultadoRodada r : historicoRepository.findTop5ByOrderByDataHoraDescIdDesc()) {
            lista.add(new RodadaDTO(
                    r.getResultado(),
                    r.getPontuacaoJogador(),
                    r.getPontuacaoDealer(),
                    r.getDataHora()));
        }
        return lista;
    }

    // Mostra a 1a carta do dealer e esconde as demais (carta com valor "?")
    private List<Carta> cartasDealerComCartaEscondida() {
        List<Carta> reais = jogo.getDealer().getCartas();
        List<Carta> visiveis = new ArrayList<>();
        for (int i = 0; i < reais.size(); i++) {
            if (i == 0) {
                visiveis.add(reais.get(i));
            } else {
                visiveis.add(new Carta("?", "?", 0));
            }
        }
        return visiveis;
    }

    // Durante a partida so contam os pontos da carta que esta a mostra
    private int pontosVisiveisDoDealer() {
        List<Carta> reais = jogo.getDealer().getCartas();
        return reais.isEmpty() ? 0 : reais.get(0).getPontos();
    }

    // Converte o status em resultado para o historico (visao do jogador)
    private String resultadoDoJogador(String status) {
        switch (status) {
            case "JOGADOR_VENCEU":
            case "DEALER_ESTOUROU":
                return "GANHOU";
            case "DEALER_VENCEU":
            case "JOGADOR_ESTOUROU":
                return "PERDEU";
            default:
                return "EMPATE";
        }
    }

    // Texto que aparece na tela quando a rodada termina
    private String mensagemPara(String status) {
        switch (status) {
            case "JOGADOR_VENCEU":
                return "Voce venceu!";
            case "DEALER_VENCEU":
                return "O dealer venceu.";
            case "EMPATE":
                return "Empate!";
            case "JOGADOR_ESTOUROU":
                return "Voce estourou! Passou de 21.";
            case "DEALER_ESTOUROU":
                return "O dealer estourou! Voce venceu.";
            default:
                return "";
        }
    }
}
