# 🃏 BlackJack (Jogador x Dealer)

Jogo de BlackJack feito para uma atividade de faculdade.

- **Back-end:** Java + Spring Boot (API REST, jogo guardado em memória)
- **Front-end:** React + Vite (tela única, CSS simples)

O jogador joga contra a máquina (dealer). Ganha quem chegar mais perto de 21 sem estourar.

---

## ✅ O que você precisa ter instalado

| Ferramenta | Versão mínima | Observação |
|------------|---------------|------------|
| **Java**   | 17 ou maior   | (testado com o Java 21) |
| **Node.js**| 18 ou maior   | já vem com o `npm` |

> Não precisa instalar o Maven! O back-end já vem com o **Maven Wrapper** (`mvnw`),
> que baixa o Maven sozinho na primeira execução.

---

## 📁 Estrutura do projeto (onde cada arquivo fica)

```
BlackJack/
│
├── backend/                         ← projeto Java (Spring Boot)
│   ├── pom.xml                      ← dependências do back-end
│   ├── mvnw / mvnw.cmd             ← Maven Wrapper (roda sem instalar Maven)
│   └── src/main/
│       ├── java/com/faculdade/blackjack/
│       │   ├── BlackjackApplication.java     ← classe que liga a aplicação
│       │   ├── model/
│       │   │   ├── Carta.java                 ← uma carta (naipe, valor, pontos)
│       │   │   ├── Baralho.java               ← cria/embaralha as 52 cartas
│       │   │   ├── Jogador.java               ← jogador e dealer (calcula pontuação)
│       │   │   ├── Jogo.java                  ← estado de uma partida
│       │   │   └── ResultadoRodada.java       ← resultado de uma rodada (histórico)
│       │   ├── dto/
│       │   │   └── EstadoJogo.java            ← resposta enviada ao front-end
│       │   ├── service/
│       │   │   └── JogoService.java           ← regras do jogo (a "inteligência")
│       │   └── controller/
│       │       └── JogoController.java        ← endpoints da API REST
│       └── resources/
│           └── application.properties         ← porta do servidor (8081)
│
└── frontend/                        ← projeto React (Vite)
    ├── package.json                ← dependências do front-end
    ├── vite.config.js              ← configuração (porta 3000)
    ├── index.html                  ← página base
    └── src/
        ├── main.jsx                ← ponto de entrada do React
        ├── App.jsx                 ← junta tudo (estado + chamadas à API)
        ├── App.css                 ← estilos (organizado por seções)
        ├── api.js                  ← funções que chamam a API do back-end
        └── components/             ← cada parte da tela em um arquivo
            ├── Carta.jsx           ← desenha uma carta
            ├── Mao.jsx             ← mão do dealer/jogador (título + pontos + cartas)
            ├── Controles.jsx       ← os botões (Novo jogo / Comprar / Parar)
            └── Historico.jsx       ← lista das últimas rodadas
```

---

## 🧠 Como o código funciona (resumo para o vídeo)

Quando você clica em um botão, acontece este caminho:

1. **Front-end** — o componente `Controles` chama uma função do `App.jsx`.
2. `App.jsx` chama o `api.js`, que faz a requisição HTTP (ex: `POST /jogo/comprar`).
3. **Back-end** — o `JogoController` recebe a rota e repassa para o `JogoService`.
4. O `JogoService` aplica as regras (usando os models `Jogo`, `Jogador`, `Baralho`, `Carta`)
   e monta um `EstadoJogo` (o objeto de resposta).
5. A resposta volta em JSON; o `App.jsx` guarda no estado e os componentes
   (`Mao`, `Carta`, `Historico`) se redesenham na tela.

Divisão de responsabilidades (bom para explicar pasta por pasta):

| Pasta | Função |
|-------|--------|
| `model/`      | os **dados** do jogo (carta, baralho, jogador, partida, resultado) |
| `service/`    | as **regras** (quem ganha, quando o dealer compra, o histórico) |
| `controller/` | a **porta de entrada** da API (as rotas `/jogo/...`) |
| `dto/`        | o **formato** dos dados enviados ao front-end |
| `components/` | cada **pedaço visual** da tela (front-end) |

---

## ▶️ Como rodar (passo a passo)

Você precisa de **dois terminais abertos ao mesmo tempo**:
um para o back-end e outro para o front-end.

### 1) Back-end (API) — porta 8081

No primeiro terminal:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

> No Linux/Mac use: `./mvnw spring-boot:run`

Espere aparecer a mensagem `Started BlackjackApplication`.
A API ficará em **http://localhost:8081**.

### 2) Front-end (tela do jogo) — porta 3000

No segundo terminal:

```powershell
cd frontend
npm install
npm run dev
```

Depois abra no navegador: **http://localhost:3000**

> Deixe os **dois terminais rodando** enquanto joga.
> Para parar qualquer um deles, pressione `Ctrl + C` no terminal.

---

## 🎮 Como jogar

1. Clique em **Novo jogo** → você e o dealer recebem 2 cartas (uma do dealer fica escondida).
2. Clique em **Comprar carta** para pegar mais cartas (cuidado para não passar de 21).
3. Clique em **Parar** quando estiver satisfeito → o dealer joga sozinho (compra até ter 17+).
4. O resultado aparece na tela. Clique em **Novo jogo** para jogar de novo.
5. Embaixo da mesa fica o **histórico das últimas rodadas** (ganhou / perdeu / empate).

**Regras de pontos:** Ás vale 1 ou 11 (o que for melhor); Valete, Dama e Rei valem 10;
as demais cartas valem o próprio número.

---

## 🌐 Endpoints da API

| Método | Rota            | O que faz                                   |
|--------|-----------------|---------------------------------------------|
| POST   | `/jogo/novo`    | Inicia um novo jogo                         |
| POST   | `/jogo/comprar` | Jogador compra uma carta                    |
| POST   | `/jogo/parar`   | Jogador para e o dealer joga automaticamente|
| GET    | `/jogo/estado`  | Retorna o estado atual do jogo              |

Exemplo de resposta:

```json
{
  "cartasJogador": [ { "naipe": "Paus", "valor": "Q", "pontos": 10 } ],
  "pontuacaoJogador": 19,
  "cartasDealer":  [ { "naipe": "Espadas", "valor": "K", "pontos": 10 } ],
  "pontuacaoDealer": 10,
  "status": "EM_ANDAMENTO",
  "mensagem": "",
  "historico": [
    { "resultado": "GANHOU", "pontuacaoJogador": 20, "pontuacaoDealer": 18 },
    { "resultado": "PERDEU", "pontuacaoJogador": 23, "pontuacaoDealer": 17 }
  ]
}
```

O campo `historico` traz as **últimas 5 rodadas** (mais nova primeiro). O back-end
guarda isso em memória no `JogoService` e o front-end mostra embaixo da mesa.

**Valores possíveis de `status`:**
`EM_ANDAMENTO`, `JOGADOR_VENCEU`, `DEALER_VENCEU`, `EMPATE`,
`JOGADOR_ESTOUROU`, `DEALER_ESTOUROU`.

---

## ❓ Problemas comuns

- **"Port 8081 already in use"** → algum programa já usa a porta. Mude o número em
  `backend/src/main/resources/application.properties` (ex: `server.port=8082`) e também
  em `frontend/src/api.js` (a `URL_BASE`).
- **A tela não atualiza / dá erro de rede** → confira se o back-end está rodando (terminal 1).
- **Usei 8081 e não 8080** de propósito, pois a porta 8080 costuma estar ocupada (Apache/XAMPP).
