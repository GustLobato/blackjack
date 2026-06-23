// Funcoes que conversam com a API do back-end (Spring Boot)

const URL_BASE = "http://localhost:8081/jogo";

// Funcao auxiliar: faz a requisicao e avisa se deu erro
async function chamar(rota, metodo) {
  const resposta = await fetch(`${URL_BASE}${rota}`, { method: metodo });
  if (!resposta.ok) {
    throw new Error("O servidor respondeu com erro " + resposta.status);
  }
  return resposta.json();
}

// Inicia um novo jogo
export async function novoJogo() {
  return chamar("/novo", "POST");
}

// Jogador compra uma carta
export async function comprarCarta() {
  return chamar("/comprar", "POST");
}

// Jogador para e o dealer joga
export async function parar() {
  return chamar("/parar", "POST");
}

// Pega o estado atual do jogo
export async function estado() {
  return chamar("/estado", "GET");
}
