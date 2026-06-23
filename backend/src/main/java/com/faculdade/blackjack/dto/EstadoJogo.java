package com.faculdade.blackjack.dto;

import java.util.List;

import com.faculdade.blackjack.model.Carta;
import com.faculdade.blackjack.model.ResultadoRodada;

// Objeto que e enviado como resposta (JSON) para o front-end.
// Reune tudo que a tela precisa para se desenhar.
public class EstadoJogo {

    private List<Carta> cartasJogador;
    private int pontuacaoJogador;
    private List<Carta> cartasDealer;
    private int pontuacaoDealer;
    private String status;   // EM_ANDAMENTO, SEM_JOGO, JOGADOR_VENCEU, ...
    private String mensagem; // texto final mostrado quando a rodada termina
    private List<ResultadoRodada> historico;

    public List<Carta> getCartasJogador() {
        return cartasJogador;
    }

    public void setCartasJogador(List<Carta> cartasJogador) {
        this.cartasJogador = cartasJogador;
    }

    public int getPontuacaoJogador() {
        return pontuacaoJogador;
    }

    public void setPontuacaoJogador(int pontuacaoJogador) {
        this.pontuacaoJogador = pontuacaoJogador;
    }

    public List<Carta> getCartasDealer() {
        return cartasDealer;
    }

    public void setCartasDealer(List<Carta> cartasDealer) {
        this.cartasDealer = cartasDealer;
    }

    public int getPontuacaoDealer() {
        return pontuacaoDealer;
    }

    public void setPontuacaoDealer(int pontuacaoDealer) {
        this.pontuacaoDealer = pontuacaoDealer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<ResultadoRodada> getHistorico() {
        return historico;
    }

    public void setHistorico(List<ResultadoRodada> historico) {
        this.historico = historico;
    }
}
