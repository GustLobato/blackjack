import Carta from "./Carta";

// Mostra a "mao" de um participante: titulo, pontuacao e as cartas
// (serve tanto para o Dealer quanto para o Jogador)

function Mao({ titulo, pontuacao, cartas }) {
  return (
    <div className="mao">
      <h2 className="mao-titulo">
        {titulo}
        <span className="pontos">{pontuacao}</span>
      </h2>

      <div className="cartas">
        {cartas.map((carta, indice) => (
          <Carta key={indice} carta={carta} />
        ))}
      </div>
    </div>
  );
}

export default Mao;
