// Mostra um historico simples das ultimas rodadas (ganhou / perdeu / empate)

function Historico({ historico, onLimpar }) {
  // Se ainda nao tem nenhuma rodada terminada, nao mostra nada
  if (!historico || historico.length === 0) {
    return null;
  }

  return (
    <div className="historico">
      <h3>Últimas rodadas</h3>
      <ul>
        {historico.map((rodada, indice) => (
          <li key={indice} className={"hist-item hist-" + rodada.resultado.toLowerCase()}>
            <span className="hist-texto">{texto(rodada.resultado)}</span>
            <span className="hist-placar">
              {rodada.pontuacaoJogador} x {rodada.pontuacaoDealer}
            </span>
          </li>
        ))}
      </ul>
      <button className="btn-limpar" onClick={onLimpar}>
        Limpar histórico
      </button>
    </div>
  );
}

// Transforma o resultado em um texto com emoji
function texto(resultado) {
  if (resultado === "GANHOU") return "✅ Você ganhou";
  if (resultado === "PERDEU") return "❌ Você perdeu";
  return "🤝 Empate";
}

export default Historico;
