# Prototype

Também conhecido como Protótipo ou Clone.

## Problema prático

Na pasta `Problema - Fase 1`, existe um gerador de relatórios.

Nesse primeiro momento, criar um relatório ainda é bem simples:

```java
return new Relatorio(
        "Resumo financeiro",
        "Equipe Financeira",
        "Janeiro/2026",
        "Corporativo");
```

O relatório tem poucos dados:

- título;
- autor;
- período;
- tema visual.

Até aqui, tudo bem.

Criar um objeto com quatro informações não parece uma dor real.

Mas vamos imaginar que esse relatório financeiro cresceu.

Agora ele tem:

- título;
- autor;
- período;
- tema visual;
- gráficos;
- resumo executivo;
- comparativo com ano anterior;
- marca d'água;
- formato de exportação.

Na pasta `Problema - Fase 2`, a gente começa a criar relatórios mensais.

Por exemplo:

- relatório financeiro de janeiro;
- relatório financeiro de fevereiro;
- relatório financeiro de março.

O detalhe é que esses relatórios são praticamente iguais.

O que muda de verdade é o período.

Mesmo assim, o código recria o relatório inteiro para cada mês:

```java
public Relatorio criarRelatorioFinanceiroJaneiro() {
    return new Relatorio(
            "Resumo financeiro",
            "Equipe Financeira",
            "Janeiro/2026",
            "Corporativo",
            true,
            true,
            true,
            "Confidencial",
            "PDF");
}
```

Depois repete quase tudo para fevereiro:

```java
public Relatorio criarRelatorioFinanceiroFevereiro() {
    return new Relatorio(
            "Resumo financeiro",
            "Equipe Financeira",
            "Fevereiro/2026",
            "Corporativo",
            true,
            true,
            true,
            "Confidencial",
            "PDF");
}
```

E repete de novo para março:

```java
public Relatorio criarRelatorioFinanceiroMarco() {
    return new Relatorio(
            "Resumo financeiro",
            "Equipe Financeira",
            "Março/2026",
            "Corporativo",
            true,
            true,
            true,
            "Confidencial",
            "PDF");
}
```

O incômodo aqui é bem específico:

> Eu já tenho um relatório praticamente pronto. Por que estou reconstruindo outro quase igual do zero?

Esse é o tipo de situação em que Prototype começa a fazer sentido.

## Percepções sobre o problema

O Prototype não nasce da mesma dor do Builder.

No Builder, a dor era:

> montar um objeto complexo passo a passo, evitando um construtor gigante.

No Prototype, a dor é:

> criar um objeto novo copiando um objeto que já está configurado.

No exemplo dos relatórios, existe uma configuração financeira padrão:

- título: `Resumo financeiro`;
- autor: `Equipe Financeira`;
- tema visual: `Corporativo`;
- incluir gráficos;
- incluir resumo executivo;
- incluir comparativo com ano anterior;
- marca d'água: `Confidencial`;
- formato: `PDF`.

Essa configuração se repete em janeiro, fevereiro, março e provavelmente nos próximos meses também.

Então a pergunta boa é:

> Eu preciso mesmo chamar `new Relatorio(...)` várias vezes, ou posso ter um relatório base e clonar esse modelo?

Pensando no mundo real, isso é bem natural.

Quando eu tenho um documento, uma apresentação, um card, um dashboard ou um relatório já formatado, muitas vezes eu não quero montar tudo do zero.

Eu quero duplicar e alterar só um detalhe.

Por exemplo:

- duplicar um documento do Google Docs;
- duplicar uma apresentação;
- duplicar um card no Trello;
- duplicar um dashboard;
- duplicar um template de e-mail;
- duplicar um relatório mensal e mudar só o mês.

É isso que o Prototype traz para o código.

## Por que não copiar por fora?

Uma ideia inicial poderia ser:

> Beleza, então eu pego os dados do relatório antigo e crio outro igual.

Só que isso joga a responsabilidade da cópia para fora do objeto.

O código de fora teria que saber tudo que existe dentro de `Relatorio`.

Se o relatório tem:

```java
private String titulo;
private String autor;
private String periodo;
private String temaVisual;
private boolean incluirGraficos;
private boolean incluirResumoExecutivo;
private boolean incluirComparativoAnoAnterior;
private String marcaDagua;
private String formatoExportacao;
```

o código de fora teria que saber copiar tudo isso.

Só que esses campos são privados.

E mesmo que existissem getters para todos eles, ainda ficaria estranho espalhar a regra de cópia fora da classe.

Imagina que amanhã entra um novo campo:

```java
private String assinaturaDigital;
```

Se a cópia estiver espalhada em vários lugares do sistema, todo lugar que copia relatório precisa lembrar desse novo campo.

Se um lugar esquecer, nasce uma cópia incompleta.

A ideia do Prototype é inverter isso:

> Quem sabe copiar um relatório é o próprio relatório.

O objeto conhece seus próprios campos.

Então ele consegue criar uma cópia de si mesmo com mais segurança.

## Solução proposta

Na solução, aparece uma interface:

```java
public interface RelatorioPrototype {
    Relatorio clonar();
}
```

Essa interface representa a ideia principal do padrão:

> objetos desse tipo sabem se clonar.

A classe `Relatorio` passa a implementar essa interface:

```java
public class Relatorio implements RelatorioPrototype {
```

E dentro dela aparece o método `clonar`:

```java
@Override
public Relatorio clonar() {
    return new Relatorio(
            this.titulo,
            this.autor,
            this.periodo,
            this.temaVisual,
            this.incluirGraficos,
            this.incluirResumoExecutivo,
            this.incluirComparativoAnoAnterior,
            this.marcaDagua,
            this.formatoExportacao);
}
```

Esse método é o coração do Prototype neste exemplo.

Ele cria um novo `Relatorio`, mas usando os dados do próprio objeto atual.

Repara no `this`:

```java
this.titulo
this.autor
this.periodo
this.temaVisual
```

Isso quer dizer:

> copia os valores deste relatório aqui para um novo relatório.

Então o clone não nasce vazio.

Ele nasce com a mesma configuração do original.

## Debugando mentalmente a solução

Na `Principal`, o fluxo ficou assim:

```java
GeradorRelatorios geradorRelatorios = new GeradorRelatorios();

Relatorio relatorioBase = geradorRelatorios.criarRelatorioFinanceiro();
```

Aqui nasce um relatório financeiro base.

Ele vem mais ou menos assim:

```text
titulo = "Resumo financeiro"
autor = "Equipe Financeira"
periodo = null
temaVisual = "Corporativo"
incluirGraficos = true
incluirResumoExecutivo = true
incluirComparativoAnoAnterior = true
marcaDagua = "Confidencial"
formatoExportacao = "PDF"
```

Esse `relatorioBase` é o modelo.

Ele ainda não representa um mês específico.

Depois vem:

```java
Relatorio relatorioMaio = relatorioBase.clonar();
relatorioMaio.definirPeriodo("Maio/2026");
```

Essa parte é muito importante.

Primeiro, `relatorioBase.clonar()` cria outro objeto `Relatorio` com os mesmos dados do base.

Depois, só o período da cópia é alterado:

```java
relatorioMaio.definirPeriodo("Maio/2026");
```

O relatório base continua sendo o modelo.

O relatório de maio é uma cópia ajustada.

Depois acontece de novo:

```java
Relatorio relatorioOutubro = relatorioBase.clonar();
relatorioOutubro.definirPeriodo("Outubro/2026");
```

Agora o mesmo modelo base gerou outra cópia.

Só que essa cópia virou outubro.

O fluxo mental é:

```text
Relatorio base
      ↓ clonar
Relatorio maio
      ↓ alterar periodo
Maio/2026

Relatorio base
      ↓ clonar
Relatorio outubro
      ↓ alterar periodo
Outubro/2026
```

O ganho aparece aqui:

> eu não recriei toda a configuração financeira para maio e outubro.

Eu copiei o modelo e alterei só o período.

## Comparando sem Prototype e com Prototype

Sem Prototype, criar maio e outubro exigiria repetir o construtor:

```java
Relatorio maio = new Relatorio(
        "Resumo financeiro",
        "Equipe Financeira",
        "Maio/2026",
        "Corporativo",
        true,
        true,
        true,
        "Confidencial",
        "PDF");

Relatorio outubro = new Relatorio(
        "Resumo financeiro",
        "Equipe Financeira",
        "Outubro/2026",
        "Corporativo",
        true,
        true,
        true,
        "Confidencial",
        "PDF");
```

Com Prototype, a criação fica assim:

```java
Relatorio relatorioBase = geradorRelatorios.criarRelatorioFinanceiro();

Relatorio relatorioMaio = relatorioBase.clonar();
relatorioMaio.definirPeriodo("Maio/2026");

Relatorio relatorioOutubro = relatorioBase.clonar();
relatorioOutubro.definirPeriodo("Outubro/2026");
```

A diferença não é só estética.

No primeiro caso, cada novo relatório precisa repetir toda a configuração.

No segundo caso, existe um modelo base.

As variações partem desse modelo.

Se amanhã a configuração padrão mudar de `PDF` para `XLSX`, o ponto natural de alteração é o relatório base.

Não cada relatório mensal espalhado no código.

## Dúvidas que parecem bobas, mas não são

### Prototype é só criar um método `clonar`?

Não é só o método existir.

O método precisa ser usado para criar variações a partir de um objeto existente.

Se a classe tem `clonar()`, mas o sistema continua criando tudo com `new Relatorio(...)`, a estrutura existe, mas o padrão ainda não está aparecendo no fluxo principal.

O clique acontece quando aparece algo assim:

```java
Relatorio relatorioMaio = relatorioBase.clonar();
```

A partir daí, o objeto novo nasce copiando um objeto que já existia.

### Por que usar `definirPeriodo` depois do clone?

Porque o clone cria uma cópia igual ao modelo.

Só que a cópia geralmente precisa mudar alguma coisa.

No exemplo, a configuração do relatório é a mesma.

O período muda.

Então primeiro eu clono:

```java
Relatorio relatorioMaio = relatorioBase.clonar();
```

Depois eu ajusto o detalhe específico:

```java
relatorioMaio.definirPeriodo("Maio/2026");
```

Isso deixa clara a intenção:

> quero um relatório igual ao base, mas para maio.

### Por que não passar o período direto no `clonar`?

Poderia existir uma variação assim:

```java
Relatorio relatorioMaio = relatorioBase.clonarComPeriodo("Maio/2026");
```

Mas, para estudar o padrão, é mais simples separar as coisas:

- `clonar()` copia;
- `definirPeriodo(...)` ajusta a cópia.

Assim fica mais fácil enxergar o papel de cada método.

O `clonar` não precisa saber qual variação será feita depois.

Ele só cria a cópia.

### Por que existe `RelatorioPrototype`?

Neste exemplo pequeno, pode parecer um pouco cerimonial.

Existe uma interface:

```java
public interface RelatorioPrototype {
    Relatorio clonar();
}
```

E só uma classe implementando:

```java
public class Relatorio implements RelatorioPrototype
```

Mesmo assim, a interface ajuda a representar a ideia do padrão:

> existe um contrato para objetos que sabem se clonar.

Em um cenário maior, poderiam existir outros protótipos:

```java
RelatorioFinanceiro
RelatorioComercial
RelatorioOperacional
```

Ou até outros tipos de documento.

Todos poderiam seguir o contrato:

```java
clonar()
```

No estudo, a interface deixa o ponto de extensão visível.

### O clone é igual ao original ou é o mesmo objeto?

Ele é igual em valores, mas não é o mesmo objeto.

Quando `clonar()` faz:

```java
return new Relatorio(...);
```

ele cria uma nova instância.

Então:

```java
Relatorio relatorioMaio = relatorioBase.clonar();
Relatorio relatorioOutubro = relatorioBase.clonar();
```

`relatorioMaio` e `relatorioOutubro` são objetos diferentes.

Por isso dá para fazer:

```java
relatorioMaio.definirPeriodo("Maio/2026");
relatorioOutubro.definirPeriodo("Outubro/2026");
```

sem um sobrescrever o período do outro.

### Isso é igual a Builder?

Não.

Builder monta um objeto passo a passo.

Prototype copia um objeto pronto.

No Builder, a cabeça está assim:

```text
vou escolher as partes e montar
```

No Prototype, a cabeça está assim:

```text
já tenho um modelo, vou copiar
```

Uma forma curta de guardar:

```text
Builder monta.
Prototype copia.
```

### O Prototype evita `new` completamente?

Não.

Esse é um detalhe importante.

Dentro do `clonar`, ainda existe:

```java
return new Relatorio(...);
```

O padrão não faz o `new` desaparecer do universo.

Ele muda o lugar onde o `new` fica.

Antes, quem precisava criar uma cópia tinha que conhecer o construtor completo.

Agora, quem quer uma cópia chama:

```java
relatorioBase.clonar();
```

E o `new` fica encapsulado dentro do próprio objeto.

## Clone superficial e clone profundo

Neste exemplo, o clone é simples.

Os campos são `String` e `boolean`.

Então copiar os valores já resolve bem.

Mas em objetos mais complexos, pode existir uma diferença importante:

```text
clone superficial
clone profundo
```

Clone superficial:

```text
copia os valores diretos, mas pode compartilhar objetos internos
```

Clone profundo:

```text
copia também os objetos internos
```

Por exemplo, se `Relatorio` tivesse uma lista mutável:

```java
private List<String> secoes;
```

seria preciso decidir:

- a cópia pode compartilhar a mesma lista?
- ou cada relatório clonado precisa ter sua própria lista?

Se compartilhar a mesma lista sem querer, alterar a cópia poderia afetar o original.

Esse é um cuidado importante quando o objeto tem referências internas mutáveis.

Para este estudo inicial, o clone simples é suficiente para entender o padrão.

## Aplicabilidade

Eu olharia para Prototype quando:

- preciso criar objetos muito parecidos com outros que já existem;
- repetir o construtor está virando cópia e cola;
- o objeto tem muitos campos já configurados;
- eu quero criar variações de um modelo base;
- o código cliente não deveria depender do construtor completo para copiar o objeto;
- copiar por fora exigiria conhecer campos privados ou detalhes internos;
- existe a ideia de template, modelo, preset, documento base ou configuração base.

No exemplo dos relatórios, Prototype faz sentido porque o relatório financeiro mensal é quase sempre o mesmo modelo.

O que muda é uma parte pequena: o período.

## Quando não usar

Eu evitaria Prototype quando o objeto é simples demais.

Por exemplo:

```java
new Relatorio("Resumo financeiro", "Equipe Financeira", "Janeiro/2026", "Corporativo");
```

Se o objeto tem poucos campos e não existe repetição de configurações, talvez um construtor normal seja suficiente.

Também evitaria se a cópia puder gerar mais confusão do que clareza.

Por exemplo, se cada relatório for muito diferente do outro, talvez não exista um protótipo real.

Nesse caso, forçar clone pode esconder diferenças importantes.

Outro cuidado é com objetos que têm estado interno complexo.

Se existe lista, mapa, anexo, conexão, recurso externo ou objeto mutável interno, é preciso pensar bem no tipo de cópia.

## Moral prática

Prototype é quando eu tenho um objeto pronto e quero criar outro parecido copiando o próprio objeto.

No exemplo:

```java
Relatorio relatorioBase = geradorRelatorios.criarRelatorioFinanceiro();

Relatorio relatorioMaio = relatorioBase.clonar();
relatorioMaio.definirPeriodo("Maio/2026");
```

A moral é:

> não reconstruir do zero aquilo que já existe como modelo.

Uma forma bem curta de guardar:

```text
Builder monta.
Prototype copia.
```

Se eu ainda estou escolhendo peça por peça, parece Builder.

Se eu já tenho um modelo pronto e quero uma cópia ajustada, começa a parecer Prototype.
