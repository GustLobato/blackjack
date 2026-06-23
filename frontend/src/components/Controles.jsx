// Componente com os botoes de controle do jogo

function Controles({ onNovoJogo, onComprar, onParar, podeJogar }) {
  return (
    <div className="botoes">
      {/* "Novo jogo" pode ser clicado a qualquer momento */}
      <button className="btn btn-novo" onClick={onNovoJogo}>
        Novo jogo
      </button>

      {/* "Comprar" e "Parar" so funcionam durante uma partida em andamento */}
      <button className="btn btn-comprar" onClick={onComprar} disabled={!podeJogar}>
        Comprar carta
      </button>
      <button className="btn btn-parar" onClick={onParar} disabled={!podeJogar}>
        Parar
      </button>
    </div>
  );
}

export default Controles;
