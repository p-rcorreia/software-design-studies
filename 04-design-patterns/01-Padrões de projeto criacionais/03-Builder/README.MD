# Builder

Também conhecido como Construtor.

## Problema prático

Na pasta `Problema - Fase 1`, temos uma construtora que sabe construir uma casa simples.

Nesse primeiro momento, a casa precisa de poucas informações:

- paredes;
- portas;
- janelas;
- teto.

Então a classe `Casa` consegue receber tudo direto no construtor:

```java
new Casa(4, 1, 2, "Telhado comum");
```

Até aqui, tudo bem.

A chamada ainda é pequena. Dá para bater o olho e entender que estamos criando uma casa com 4 paredes, 1 porta, 2 janelas e um teto comum.

Mas vamos imaginar que o sistema da construtora cresceu.

Agora não existe apenas "uma casa simples". Alguns clientes querem casa maior, outros querem casa com jardim, outros querem piscina, outros querem garagem, outros querem aquecimento, outros querem uma observação especial.

Na pasta `Problema - Fase 2`, a casa passou a ter várias possibilidades:

- paredes;
- portas;
- janelas;
- teto;
- garagem;
- jardim;
- piscina;
- aquecimento;
- encanamento;
- energia elétrica;
- material;
- observação.

A primeira tentativa foi colocar tudo isso no construtor de `Casa`.

Aí a criação de uma casa simples fica assim:

```java
return new Casa(
        4,
        1,
        2,
        "Telhado comum",
        false,
        false,
        false,
        false,
        true,
        true,
        "Alvenaria",
        null);
```

Esse é o tipo de construtor que a literatura costuma chamar de construtor telescópico.

Ele vai crescendo conforme novas opções aparecem.

No início era pequeno. Depois entrou garagem. Depois jardim. Depois piscina. Depois aquecimento. Depois material. Depois observação.

E a chamada vai ficando cada vez mais comprida.

O problema não é só ficar feio.

O problema é que fica difícil ler e fácil errar.

Quando eu vejo:

```java
false,
false,
false,
false,
true,
true,
```

eu preciso voltar na assinatura do construtor para lembrar o que cada `false` e cada `true` significa.

Esse primeiro `false` é garagem?

O segundo é jardim?

O terceiro é piscina?

O quarto é aquecimento?

E os dois `true` são encanamento e energia elétrica?

O código compila, mas a leitura fica cansativa.

## Percepções sobre o problema

O problema aqui não é simplesmente "uma casa ter muitos atributos".

Uma casa pode mesmo ter várias partes. Isso faz sentido no domínio.

O problema aparece quando a criação da casa depende de uma lista grande de parâmetros posicionais.

Parâmetros posicionais são aqueles em que a ordem manda em tudo.

Por exemplo:

```java
new Casa(8, 3, 6, "Telhado colonial", true, true, true, false, true, true, "Alvenaria", "Área externa com churrasqueira");
```

Essa linha cria uma casa com piscina. Mas ela exige que quem está lendo saiba decorar a ordem dos parâmetros.

Se eu inverter dois valores do mesmo tipo, o Java pode nem reclamar.

Por exemplo, `material` e `observacao` são `String`. Se alguém trocar a ordem, o código ainda pode compilar, mas a casa fica conceitualmente errada.

Com `boolean`, a situação fica ainda pior para leitura.

`true` sozinho não explica nada.

`false` sozinho também não.

Um `false` pode significar "sem garagem", "sem jardim", "sem piscina" ou "sem aquecimento". Quem dá significado para ele é a posição dentro do construtor.

Uma analogia bem simples:

Imagina contratar uma construtora e ela te entregar um formulário assim:

```text
4, 1, 2, Telhado comum, não, não, não, não, sim, sim, Alvenaria, vazio
```

Dá para processar?

Dá.

Mas você precisa de uma legenda do lado.

Agora compara com:

```text
Construir 4 paredes.
Instalar 1 porta.
Instalar 2 janelas.
Construir telhado comum.
Instalar encanamento.
Instalar energia elétrica.
Usar material de alvenaria.
```

A segunda forma parece mais longa em número de linhas, mas é muito mais clara.

No código, Builder vai nessa direção: transformar uma lista confusa de parâmetros em uma montagem passo a passo.

## Solução - Fase 1: Builder sem diretor

Na `Solução - Fase 1`, a gente começa a resolver o problema criando um Builder.

As peças principais são:

- `Casa`: o produto final;
- `CasaBuilder`: o contrato das etapas de construção;
- `CasaBuilderPadrao`: a implementação que guarda os valores durante a montagem;
- `Construtora`: por enquanto, ainda guarda a receita da casa simples;
- `Principal`: chama a construtora e exibe a casa.

O ponto mais importante: o `CasaBuilderPadrao` não deve apenas imprimir mensagens.

Ele guarda estado.

Quando chamamos:

```java
builder.construirParedes(4);
```

o builder faz:

```java
this.paredes = 4;
```

Quando chamamos:

```java
builder.construirPortas(1);
```

o builder faz:

```java
this.portas = 1;
```

Ou seja, o builder vai sendo preenchido aos poucos.

Ele funciona como uma construção em andamento.

### O que mudou na Construtora

Antes, a `Construtora` criava a casa com o construtor gigante:

```java
return new Casa(
        4,
        1,
        2,
        "Telhado comum",
        false,
        false,
        false,
        false,
        true,
        true,
        "Alvenaria",
        null);
```

Depois do Builder, a `Construtora` passou a fazer:

```java
CasaBuilder builder = new CasaBuilderPadrao();

builder.construirParedes(4);
builder.construirPortas(1);
builder.construirJanelas(2);
builder.construirTeto("Telhado comum");
builder.construirEncanamento();
builder.construirEnergiaEletrica();
builder.definirMaterial("Alvenaria");

return builder.construirCasa();
```

Aqui tem um ganho importante.

Não ficou menor.

Na verdade, ficou maior.

Mas ficou muito mais legível.

Antes eu via:

```java
false, false, false, false, true, true, "Alvenaria", null
```

Agora eu vejo:

```java
builder.construirEncanamento();
builder.construirEnergiaEletrica();
builder.definirMaterial("Alvenaria");
```

O código parou de ser uma lista de valores soltos e virou uma sequência de decisões nomeadas.

### E os booleanos?

Uma dúvida importante foi: por que `construirPiscina()` não recebe `true` ou `false`?

Porque, nesse caso, a chamada do método já significa `true`.

Se eu quero piscina, eu chamo:

```java
builder.construirPiscina();
```

Se eu não quero piscina, eu simplesmente não chamo esse método.

Isso evita voltar ao problema original:

```java
builder.construirPiscina(false);
builder.construirGaragem(false);
builder.construirJardim(false);
```

Para campos de quantidade ou texto, faz sentido receber parâmetro:

```java
builder.construirParedes(4);
builder.construirPortas(1);
builder.construirTeto("Telhado comum");
builder.definirMaterial("Alvenaria");
```

Para opcionais booleanos, chamar o método já comunica intenção:

```java
builder.construirJardim();
builder.construirPiscina();
builder.construirAquecimento();
```

E a ausência da chamada comunica que aquela parte não existe.

Isso é bem elegante no Builder.

### Por que CasaBuilder e CasaBuilderPadrao?

Outra dúvida boa foi: não parece redundante ter `CasaBuilder` e `CasaBuilderPadrao`?

Sim, neste exemplo pequeno parece um pouco redundante.

Hoje existe só uma implementação concreta:

```java
CasaBuilderPadrao
```

Então a interface `CasaBuilder` não está resolvendo uma dor gigante ainda.

Ela está mostrando o ponto de extensão do padrão.

Se amanhã existisse outro jeito de construir ou outro resultado de construção, o contrato já estaria ali.

Por exemplo, em um caso mais clássico, poderia existir:

```java
CasaBuilderPadrao
ManualDaCasaBuilder
```

Os mesmos passos poderiam gerar uma `Casa` ou um manual da casa.

No nosso estudo, a frase boa é:

> A interface `CasaBuilder` não está resolvendo uma dor forte neste exemplo pequeno. Ela está ensinando onde o padrão permite variar.

Em código de produção muito simples, talvez eu começasse sem interface.

Em estudo de design pattern, faz sentido manter para enxergar a arquitetura.

### O ganho real da Fase 1

O ganho da Fase 1 não é "escrever menos código".

O ganho é parar de criar a casa com uma chamada opaca.

Antes:

```java
new Casa(4, 1, 2, "Telhado comum", false, false, false, false, true, true, "Alvenaria", null);
```

Depois:

```java
builder.construirParedes(4);
builder.construirPortas(1);
builder.construirJanelas(2);
builder.construirTeto("Telhado comum");
builder.construirEncanamento();
builder.construirEnergiaEletrica();
builder.definirMaterial("Alvenaria");
```

Numa situação real, se você não tivesse feito isso, toda nova casa precisaria lembrar a ordem exata dos parâmetros.

Se alguém colocasse `true` no lugar errado, a casa poderia sair com piscina quando queria garagem, ou com aquecimento quando queria encanamento.

O Java poderia aceitar.

Mas o negócio estaria errado.

Com Builder, a intenção fica mais difícil de esconder.

## Solução - Fase 2: Builder com diretor

Na `Solução - Fase 2`, entra o `DiretorConstrucao`.

Aí vem a parte que parece meio doida mesmo.

Na `Principal`, a `Construtora` e a criação direta da `Casa` saem de cena.

Entram:

```java
DiretorConstrucao diretorConstrucao = new DiretorConstrucao();
CasaBuilderPadrao builder = new CasaBuilderPadrao();
```

Depois:

```java
diretorConstrucao.construirCasaComPiscina(builder);
```

E só depois:

```java
Casa casa = builder.construirCasa();
```

Esse fluxo parece estranho no começo porque o diretor não retorna uma casa.

Mas esse é justamente o ponto.

O diretor não é o produto.

O diretor não é quem guarda os dados.

O diretor não é quem chama o construtor final.

O diretor só sabe a receita.

### Debugando mentalmente a Principal

Vamos simular essa parte:

```java
DiretorConstrucao diretorConstrucao = new DiretorConstrucao();
CasaBuilderPadrao builder = new CasaBuilderPadrao();
```

Nesse momento, temos dois objetos.

O `diretorConstrucao` tem métodos como:

```java
construirCasaSimples(builder)
construirCasaComPiscina(builder)
construirCasaDeMadeira(builder)
```

Ele sabe receitas.

O `builder` acabou de nascer vazio.

Internamente, ele está mais ou menos assim:

```text
paredes = 0
portas = 0
janelas = 0
teto = null
garagem = false
jardim = false
piscina = false
aquecimento = false
encanamento = false
energiaEletrica = false
material = null
observacao = null
```

Agora a `Principal` chama:

```java
diretorConstrucao.construirCasaComPiscina(builder);
```

Essa linha quer dizer:

> Diretor, aplique a receita de casa com piscina nesse builder aqui.

Dentro do `DiretorConstrucao`, esse método faz:

```java
builder.construirParedes(8);
builder.construirPortas(3);
builder.construirJanelas(6);
builder.construirTeto("Telhado colonial");
builder.construirGaragem();
builder.construirJardim();
builder.construirPiscina();
builder.construirEncanamento();
builder.construirEnergiaEletrica();
builder.definirMaterial("Alvenaria");
builder.adicionarObservacao("Área externa com churrasqueira");
```

O diretor está só chamando passos.

Quem guarda os valores é o `CasaBuilderPadrao`.

Depois dessa chamada, o builder ficou mais ou menos assim:

```text
paredes = 8
portas = 3
janelas = 6
teto = "Telhado colonial"
garagem = true
jardim = true
piscina = true
aquecimento = false
encanamento = true
energiaEletrica = true
material = "Alvenaria"
observacao = "Área externa com churrasqueira"
```

Agora sim vem:

```java
Casa casa = builder.construirCasa();
```

Essa linha quer dizer:

> Builder, com tudo que você acumulou até agora, me entrega uma Casa.

Então o `CasaBuilderPadrao` executa:

```java
return new Casa(
        paredes,
        portas,
        janelas,
        teto,
        garagem,
        jardim,
        piscina,
        aquecimento,
        encanamento,
        energiaEletrica,
        material,
        observacao);
```

Só nesse momento a `Casa` nasce.

Por isso o diretor não precisa retornar `Casa`.

Ele não cria o produto final.

Ele configura o builder.

### O diretor é um meio de campo?

Sim, dá para pensar assim.

Mas com uma palavra mais precisa:

> O diretor orquestra.

Ele fica no meio entre a intenção e os passos.

Quando eu chamo:

```java
diretorConstrucao.construirCasaComPiscina(builder);
```

eu não quero escrever a sequência completa na `Principal`.

Eu só quero dizer:

> aplica a receita da casa com piscina.

O diretor sabe essa receita.

O builder sabe executar os passos.

A casa é o resultado final.

O fluxo é:

```text
Principal escolhe a receita
        ↓
DiretorConstrucao chama os passos
        ↓
CasaBuilderPadrao guarda os valores
        ↓
CasaBuilderPadrao constrói a Casa
        ↓
Casa exibe os detalhes
```

### Por que trocar casa simples por casa com piscina funcionaria?

Porque você não troca a ferramenta.

Você troca a receita.

Mesma ferramenta:

```java
CasaBuilderPadrao builder = new CasaBuilderPadrao();
```

Receita diferente:

```java
diretorConstrucao.construirCasaSimples(builder);
```

ou:

```java
diretorConstrucao.construirCasaComPiscina(builder);
```

ou:

```java
diretorConstrucao.construirCasaDeMadeira(builder);
```

O builder continua sendo capaz de montar uma casa.

O diretor muda quais passos serão usados.

### O que o Diretor resolveu?

Na Fase 1, a sequência da casa simples estava na `Construtora`.

Isso aqui era uma receita:

```java
builder.construirParedes(4);
builder.construirPortas(1);
builder.construirJanelas(2);
builder.construirTeto("Telhado comum");
builder.construirEncanamento();
builder.construirEnergiaEletrica();
builder.definirMaterial("Alvenaria");
```

Se eu quisesse criar várias casas, poderia acabar criando várias receitas na `Construtora`:

```java
construirCasaSimples()
construirCasaComPiscina()
construirCasaDeMadeira()
construirCasaLuxo()
construirCasaPraia()
```

Não é necessariamente absurdo.

Mas a `Construtora` começaria a virar um depósito de receitas.

E isso começa a encostar no Single Responsibility Principle.

SRP, numa leitura prática:

> Uma classe deveria ter um motivo principal para mudar.

Se a `Construtora` muda porque mudou a casa simples, mudou a casa com piscina, mudou a casa de madeira, entrou casa de praia, saiu casa de luxo, ela começou a ter vários motivos para mudar.

O `DiretorConstrucao` ajuda a separar:

- `CasaBuilderPadrao`: sabe como aplicar cada etapa;
- `DiretorConstrucao`: sabe quais etapas formam cada receita;
- `Casa`: é o produto final;
- `Principal`: escolhe o que quer construir.

Então, sim, Builder conversa com SRP aqui.

Mas o foco principal do Builder ainda é a construção passo a passo.

O Diretor é a evolução que organiza receitas reutilizáveis.

## Dúvidas que parecem bobas, mas não são

### Preciso criar uma interface Casa?

Não.

Neste estudo, `Casa` pode continuar sendo uma classe concreta.

Ela é o produto final.

A interface importante é `CasaBuilder`, porque ela descreve as etapas de construção.

Você só criaria uma interface para `Casa` se tivesse produtos finais diferentes com um contrato comum.

Por exemplo, se os mesmos passos pudessem gerar uma `Casa`, um `ManualDaCasa` ou outro tipo de resultado.

Para este estudo:

- `Casa`: classe concreta;
- `CasaBuilder`: contrato do processo;
- `CasaBuilderPadrao`: implementação concreta do processo.

### Preciso criar CasaBuilderMadeira e CasaBuilderAlvenaria?

Não necessariamente.

Se a diferença é só:

```java
builder.definirMaterial("Madeira");
```

ou:

```java
builder.definirMaterial("Alvenaria");
```

então não precisa criar outro builder.

Isso é só uma configuração do mesmo builder.

Você pensaria em builders diferentes se o jeito de construir mudasse muito.

Por exemplo:

- outro builder gera um manual em vez de uma casa;
- outro builder aplica validações completamente diferentes;
- outro builder produz outro tipo de objeto final.

Para o nosso caso, `CasaBuilderPadrao` dá conta.

Madeira e alvenaria entram como valores.

### Por que o Diretor não retorna Casa?

Porque ele não é quem cria a casa.

Ele só aplica uma receita no builder.

Essa linha:

```java
diretorConstrucao.construirCasaComPiscina(builder);
```

não quer dizer:

> Diretor, me devolva uma casa com piscina.

Ela quer dizer:

> Diretor, configure esse builder para uma casa com piscina.

Depois, quem devolve a casa é:

```java
builder.construirCasa();
```

Esse detalhe é o que mais dá aquela sensação de "muito doido" no começo.

Mas depois que clica, fica claro:

> Diretor sabe a receita. Builder guarda a construção. Casa nasce no final.

### O Diretor é obrigatório?

Não.

Builder funciona sem Diretor.

A Fase 1 já é Builder.

O Diretor entra quando você tem receitas conhecidas e quer reaproveitar essas sequências.

Se você só constrói uma casa de vez em quando, talvez chamar o builder direto seja suficiente.

Se você tem várias receitas conhecidas, o Diretor começa a fazer sentido.

### Por que criei mais código?

Porque design pattern nem sempre é sobre escrever menos.

Muitas vezes é sobre colocar a complexidade em lugares mais claros.

Antes, a complexidade estava escondida numa linha:

```java
new Casa(..., false, false, true, true, ..., null)
```

Depois, a complexidade ficou explícita:

```java
builder.construirPiscina();
builder.construirJardim();
builder.definirMaterial("Alvenaria");
```

E depois ficou organizada:

```java
diretorConstrucao.construirCasaComPiscina(builder);
```

O código aumentou.

Mas a intenção ficou mais fácil de acompanhar.

## Aplicabilidade

Eu olharia para Builder quando:

- o construtor tem muitos parâmetros;
- vários parâmetros são opcionais;
- a criação do objeto tem muitos `null`, `false` ou valores padrão;
- fica fácil inverter parâmetros da mesma classe, como duas `String`;
- existem várias formas conhecidas de montar o mesmo objeto;
- a montagem precisa acontecer passo a passo;
- o código cliente está cheio de detalhes de construção;
- eu quero reaproveitar uma sequência de montagem com um diretor.

No exemplo da casa, Builder faz sentido porque não estamos apenas escolhendo uma classe concreta.

Estamos montando um objeto complexo.

E essa montagem pode mudar conforme o tipo de casa.

Uma casa simples, uma casa com piscina e uma casa de madeira ainda são casas, mas o caminho de construção delas não precisa ser idêntico.

## Quando não usar

Eu evitaria usar Builder quando o objeto é simples demais.

Por exemplo:

```java
new Casa(4, 1, 2, "Telhado comum");
```

Se o sistema nunca passar disso, talvez um construtor normal seja suficiente.

Também evitaria usar Builder se não existem opcionais relevantes nem processo de montagem.

Se eu crio um objeto pequeno com dois ou três campos obrigatórios, criar builder pode deixar o código mais espalhado sem necessidade.

Outro cuidado: Builder adiciona mais peças ao projeto.

No nosso estudo, além de `Casa`, aparecem:

- `CasaBuilder`;
- `CasaBuilderPadrao`;
- `DiretorConstrucao`.

Isso é bom quando existe complexidade real.

Mas pode ser exagero quando a criação já está clara.

Então a pergunta prática seria:

> Eu estou apenas chamando um construtor simples, ou estou tentando representar uma montagem cheia de etapas e opcionais?

Se for uma montagem cheia de etapas, Builder pode ajudar bastante.

Se for só um objeto pequeno, talvez seja melhor continuar simples.

## Moral prática

Builder é quando eu paro de jogar todos os dados no construtor de uma vez e passo a montar o objeto por etapas nomeadas.

Na Fase 1, eu ganho clareza nos passos.

Na Fase 2, com Diretor, eu ganho reaproveitamento de receitas.

Uma forma bem curta de guardar:

```text
Casa é o produto.
CasaBuilder é o contrato dos passos.
CasaBuilderPadrao executa os passos e guarda os dados.
DiretorConstrucao sabe receitas prontas.
Principal escolhe a receita e pede a casa pronta ao builder.
```

E talvez a frase mais importante:

> O Builder sabe montar peça por peça. O Diretor sabe a receita inteira.
