package com.faculdade.blackjack.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidade JPA: cada objeto desta classe vira uma LINHA na tabela "resultado_rodada".
// Guarda o resultado de uma rodada ja terminada (do ponto de vista do jogador).
@Entity
@Table(name = "resultado_rodada")
public class ResultadoRodada {

    // Chave primaria, gerada automaticamente pelo banco (auto-incremento)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String resultado; // GANHOU, PERDEU ou EMPATE

    @Column(name = "pontuacao_jogador", nullable = false)
    private int pontuacaoJogador;

    @Column(name = "pontuacao_dealer", nullable = false)
    private int pontuacaoDealer;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    public ResultadoRodada() {
    }

    public ResultadoRodada(String resultado, int pontuacaoJogador, int pontuacaoDealer) {
        this.resultado = resultado;
        this.pontuacaoJogador = pontuacaoJogador;
        this.pontuacaoDealer = pontuacaoDealer;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getPontuacaoJogador() {
        return pontuacaoJogador;
    }

    public void setPontuacaoJogador(int pontuacaoJogador) {
        this.pontuacaoJogador = pontuacaoJogador;
    }

    public int getPontuacaoDealer() {
        return pontuacaoDealer;
    }

    public void setPontuacaoDealer(int pontuacaoDealer) {
        this.pontuacaoDealer = pontuacaoDealer;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
