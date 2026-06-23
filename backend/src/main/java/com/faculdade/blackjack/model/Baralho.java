package com.faculdade.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Cria as 52 cartas, embaralha e entrega uma carta de cada vez
public class Baralho {

    private static final String[] NAIPES = {"Copas", "Ouros", "Paus", "Espadas"};
    private static final String[] VALORES =
            {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    private final List<Carta> cartas = new ArrayList<>();

    public Baralho() {
        criarCartas();
        Collections.shuffle(cartas);
    }

    // Monta as 13 cartas de cada um dos 4 naipes (13 x 4 = 52)
    private void criarCartas() {
        for (String naipe : NAIPES) {
            for (String valor : VALORES) {
                cartas.add(new Carta(naipe, valor, pontosDoValor(valor)));
            }
        }
    }

    // Regra de pontos: As = 11, figuras = 10, demais = o proprio numero
    private int pontosDoValor(String valor) {
        switch (valor) {
            case "A":
                return 11;
            case "J":
            case "Q":
            case "K":
                return 10;
            default:
                return Integer.parseInt(valor);
        }
    }

    // Tira a carta do topo do baralho
    public Carta comprar() {
        return cartas.remove(cartas.size() - 1);
    }
}
