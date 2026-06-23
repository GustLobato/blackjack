import React, { useState } from "react";
import * as api from "./api";
import Controles from "./components/Controles";
import Mao from "./components/Mao";
import Historico from "./components/Historico";

function App() {
  // Estado do jogo que vem da API
  const [jogo, setJogo] = useState(null);
  // Mensagem de erro (ex: back-end desligado)
  const [erro, setErro] = useState(null);

  // O jogo esta acontecendo agora? (libera os botoes Comprar/Parar)
  const emAndamento = jogo && jogo.status === "EM_ANDAMENTO";
  // O jogo terminou? (mostra a mensagem final)
  const jogoTerminou =
    jogo && jogo.status !== "EM_ANDAMENTO" && jogo.status !== "SEM_JOGO";

  // Chama a API tratando erro de conexao (evita falha silenciosa)
  async function executar(acao) {
    try {
      const dados = await acao();
      setJogo(dados);
      setErro(null);
    } catch (e) {
      setErro(
        "Não foi possível conectar ao servidor. O back-end está rodando na porta 8081?"
      );
    }
  }

  return (
    <div className="container">
      <h1 className="titulo">🃏 BlackJack</h1>

      <Controles
        onNovoJogo={() => executar(api.novoJogo)}
        onComprar={() => executar(api.comprarCarta)}
        onParar={() => executar(api.parar)}
        podeJogar={emAndamento}
      />

      {/* Mensagem de erro de conexao, se houver */}
      {erro && <p className="erro">{erro}</p>}

      {/* A mesa so aparece depois que o jogo comeca */}
      {jogo && jogo.status !== "SEM_JOGO" && (
        <div className="mesa">
          <Mao
            titulo="Dealer"
            pontuacao={jogo.pontuacaoDealer}
            cartas={jogo.cartasDealer}
          />

          <div className="divisoria" />

          <Mao
            titulo="Você"
            pontuacao={jogo.pontuacaoJogador}
            cartas={jogo.cartasJogador}
          />

          {/* Mensagem final, colorida conforme o resultado */}
          {jogoTerminou && (
            <p className={"mensagem " + classeResultado(jogo.status)}>
              {jogo.mensagem}
            </p>
          )}
        </div>
      )}

      {/* Historico das ultimas rodadas, embaixo */}
      {jogo && <Historico historico={jogo.historico} />}
    </div>
  );
}

// Define a cor da mensagem final de acordo com o status
function classeResultado(status) {
  if (status === "JOGADOR_VENCEU" || status === "DEALER_ESTOUROU") return "venceu";
  if (status === "DEALER_VENCEU" || status === "JOGADOR_ESTOUROU") return "perdeu";
  return "empate";
}

export default App;
