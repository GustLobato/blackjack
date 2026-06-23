// Componente que desenha UMA carta na tela

function Carta({ carta }) {
  // A carta escondida do dealer vem com valor "?"
  const escondida = carta.valor === "?";
  if (escondida) {
    // Mostra o "verso" da carta (carta virada para baixo)
    return <div className="carta carta-virada"></div>;
  }

  // Copas e Ouros sao vermelhas; Paus e Espadas sao pretas
  const vermelha = carta.naipe === "Copas" || carta.naipe === "Ouros";
  const simbolo = naipeSimbolo(carta.naipe);

  return (
    <div className={vermelha ? "carta vermelha" : "carta"}>
      <span className="carta-canto cima">{carta.valor}{simbolo}</span>
      <span className="carta-centro">{simbolo}</span>
      <span className="carta-canto baixo">{carta.valor}{simbolo}</span>
    </div>
  );
}

// Converte o nome do naipe no simbolo correspondente
function naipeSimbolo(naipe) {
  if (naipe === "Copas") return "♥";
  if (naipe === "Ouros") return "♦";
  if (naipe === "Paus") return "♣";
  if (naipe === "Espadas") return "♠";
  return "";
}

export default Carta;
