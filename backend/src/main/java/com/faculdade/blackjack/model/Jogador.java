package com.faculdade.blackjack.model;

import java.util.ArrayList;
import java.util.List;

// Um participante da partida (serve tanto para o jogador quanto para o dealer).
// Guarda as cartas na mao e sabe calcular a propria pontuacao.
public class Jogador {

    private final List<Carta> cartas = new ArrayList<>();

    public void receber(Carta carta) {
        cartas.add(carta);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    // Soma os pontos. O As vale 11, mas se passar de 21 ele passa a valer 1.
    public int pontuacao() {
        int total = 0;
        int ases = 0;

        for (Carta carta : cartas) {
            total += carta.getPontos();
            if ("A".equals(carta.getValor())) {
                ases++;
            }
        }

        // Enquanto estourar e ainda houver As valendo 11, troca um As para 1
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }

        return total;
    }

    public boolean estourou() {
        return pontuacao() > 21;
    }
}
