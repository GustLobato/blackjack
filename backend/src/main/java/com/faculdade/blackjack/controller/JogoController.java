package com.faculdade.blackjack.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faculdade.blackjack.dto.EstadoJogo;
import com.faculdade.blackjack.service.JogoService;

// Porta de entrada da API REST. Cada rota chama o JogoService e devolve o estado.
@RestController
@RequestMapping("/jogo")
public class JogoController {

    private final JogoService servico;

    public JogoController(JogoService servico) {
        this.servico = servico;
    }

    @PostMapping("/novo")
    public EstadoJogo novo() {
        return servico.novoJogo();
    }

    @PostMapping("/comprar")
    public EstadoJogo comprar() {
        return servico.comprar();
    }

    @PostMapping("/parar")
    public EstadoJogo parar() {
        return servico.parar();
    }

    @GetMapping("/estado")
    public EstadoJogo estado() {
        return servico.estadoAtual();
    }

    // Apaga o historico de rodadas do banco e devolve o estado atualizado
    @DeleteMapping("/historico")
    public EstadoJogo limparHistorico() {
        servico.limparHistorico();
        return servico.estadoAtual();
    }
}
