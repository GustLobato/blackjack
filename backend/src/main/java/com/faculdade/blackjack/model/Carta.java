package com.faculdade.blackjack.model;

// Representa UMA carta do baralho: naipe, valor e quantos pontos vale
public class Carta {

    private String naipe; // Copas, Ouros, Paus, Espadas
    private String valor; // A, 2..10, J, Q, K (ou "?" quando esta escondida)
    private int pontos;   // quanto a carta vale (A = 11 por padrao; J/Q/K = 10)

    public Carta() {
    }

    public Carta(String naipe, String valor, int pontos) {
        this.naipe = naipe;
        this.valor = valor;
        this.pontos = pontos;
    }

    public String getNaipe() {
        return naipe;
    }

    public String getValor() {
        return valor;
    }

    public int getPontos() {
        return pontos;
    }
}
