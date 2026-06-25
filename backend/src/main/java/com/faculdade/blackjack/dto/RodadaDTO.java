package com.faculdade.blackjack.dto;

import java.time.LocalDateTime;

// DTO de saida: o formato em que uma rodada do historico e enviada ao front.
// Usamos um DTO em vez da entidade para nao expor a tabela direto na API.
public class RodadaDTO {

    private String resultado;
    private int pontuacaoJogador;
    private int pontuacaoDealer;
    private LocalDateTime dataHora;

    public RodadaDTO(String resultado, int pontuacaoJogador, int pontuacaoDealer, LocalDateTime dataHora) {
        this.resultado = resultado;
        this.pontuacaoJogador = pontuacaoJogador;
        this.pontuacaoDealer = pontuacaoDealer;
        this.dataHora = dataHora;
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}
