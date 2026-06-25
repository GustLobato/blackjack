package com.faculdade.blackjack.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faculdade.blackjack.entity.ResultadoRodada;

// Interface de acesso ao banco. Estendendo JpaRepository, o Spring Data ja
// fornece sozinho os metodos prontos: save, findAll, deleteAll, findById, etc.
public interface ResultadoRodadaRepository extends JpaRepository<ResultadoRodada, Long> {

    // Consulta personalizada criada so pelo nome do metodo:
    // pega as 5 ultimas rodadas (mais novas primeiro).
    List<ResultadoRodada> findTop5ByOrderByDataHoraDescIdDesc();
}
