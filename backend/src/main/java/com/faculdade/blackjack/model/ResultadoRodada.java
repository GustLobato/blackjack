package com.faculdade.blackjack.model;

// Resultado de uma rodada ja terminada, usado no historico.
// resultado e sempre do ponto de vista do jogador: GANHOU, PERDEU ou EMPATE.
public class ResultadoRodada {

    private String resultado;
    private int pontuacaoJogador;
    private int pontuacaoDealer;

    public ResultadoRodada() {
    }

    public ResultadoRodada(String resultado, int pontuacaoJogador, int pontuacaoDealer) {
        this.resultado = resultado;
        this.pontuacaoJogador = pontuacaoJogador;
        this.pontuacaoDealer = pontuacaoDealer;
    }

    public String getResultado() {
        return resultado;
    }

    public int getPontuacaoJogador() {
        return pontuacaoJogador;
    }

    public int getPontuacaoDealer() {
        return pontuacaoDealer;
    }
}
