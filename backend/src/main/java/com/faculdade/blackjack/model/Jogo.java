package com.faculdade.blackjack.model;

// Guarda o estado de UMA partida: o baralho, as duas maos e o status atual
public class Jogo {

    private final Baralho baralho = new Baralho();
    private final Jogador jogador = new Jogador();
    private final Jogador dealer = new Jogador();
    private String status = "EM_ANDAMENTO";

    public Baralho getBaralho() {
        return baralho;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Jogador getDealer() {
        return dealer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
