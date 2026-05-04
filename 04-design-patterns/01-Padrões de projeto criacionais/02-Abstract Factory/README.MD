# Abstract Factory

Também conhecido como Fábrica Abstrata.

## Problema prático

Na pasta `Problema - Fase 1`, temos uma loja de móveis que inicialmente trabalha apenas com uma linha moderna. Quando a loja precisa montar uma sala, ela cria diretamente uma `CadeiraModerna`, um `SofaModerno` e uma `MesaDeCentroModerna`.

Nesse primeiro momento, parece tudo bem. A loja só vende um estilo, então todos os móveis combinam naturalmente.

Mas vamos imaginar que essa loja cresceu e agora também quer vender móveis vitorianos. A sala ainda precisa ser montada com os mesmos tipos de produto: cadeira, sofá e mesa de centro. A diferença é que agora cada produto pode existir em mais de um estilo.

Na pasta `Problema - Fase 2`, a loja tenta resolver isso colocando uma decisão dentro de `LojaMoveis`. Se o estilo for `moderno`, ela cria os móveis modernos. Se o estilo for `vitoriano`, ela cria os móveis vitorianos.

O problema é que agora a loja precisa conhecer todas as classes concretas de cada estilo. E pior: como cada móvel é criado separadamente, fica fácil cometer um erro e misturar objetos que não deveriam estar juntos.

No exemplo da Fase 2, a sala vitoriana cria `CadeiraVitoriana` e `SofaVitoriano`, mas ainda usa `MesaDeCentroModerna`. O código compila e roda, mas o resultado conceitual está errado: a família de móveis deixou de combinar.

## Percepções sobre o problema

O problema aqui não é apenas ter muitos objetos. O ponto principal é que esses objetos pertencem a famílias.

Uma cadeira moderna combina com um sofá moderno e com uma mesa de centro moderna. Uma cadeira vitoriana combina com um sofá vitoriano e com uma mesa de centro vitoriana. Quando a gente cria cada objeto separadamente, o código deixa a responsabilidade de manter essa combinação espalhada na mão de quem está montando a sala.

Na Fase 1, isso quase não aparece, porque só existe uma família de produtos. Mas, na Fase 2, quando entra uma nova família, a `LojaMoveis` passa a misturar duas responsabilidades:

- montar uma sala;
- decidir quais classes concretas de cada estilo devem ser criadas.

Se no futuro aparecerem outros estilos, como `ArtDeco`, `Industrial` ou `Minimalista`, esse método tende a crescer com mais condições e mais combinações possíveis.

O risco maior não é só escrever mais código. O risco é criar uma combinação inválida sem perceber, como uma sala com cadeira de um estilo e mesa de outro.

## Solução proposta

Na solução, a gente para de deixar a `LojaMoveis` escolher manualmente quais classes concretas ela precisa criar.

Em vez de a loja fazer algo como:

- se for moderno, cria `CadeiraModerna`, `SofaModerno` e `MesaDeCentroModerna`;
- se for vitoriano, cria `CadeiraVitoriana`, `SofaVitoriano` e `MesaDeCentroVitoriana`;

ela passa a depender de uma fábrica:

```java
public class LojaMoveis {

    private FabricaDeMoveis fabricaDeMoveis;

    public LojaMoveis(FabricaDeMoveis fabricaDeMoveis){
        this.fabricaDeMoveis = fabricaDeMoveis;
    }

    public void montarSala() {
        Cadeira cadeira = fabricaDeMoveis.criarCadeira();
        MesaDeCentro mesaDeCentro = fabricaDeMoveis.criarMesaDeCentro();
        Sofa sofa = fabricaDeMoveis.criarSofa();

        cadeira.sentar();
        mesaDeCentro.apoiarObjeto();
        sofa.deitar();
    }
}
```

Repara no ponto principal: a loja ainda monta a sala, porque essa continua sendo a responsabilidade dela. O que saiu de dentro dela foi a decisão sobre qual família de móveis deve ser criada.

Essa decisão agora fica nas fábricas concretas.

A `FabricaDeMoveisModernos` cria a família moderna:

- `CadeiraModerna`;
- `MesaDeCentroModerna`;
- `SofaModerno`.

A `FabricaDeMoveisVitorianos` cria a família vitoriana:

- `CadeiraVitoriana`;
- `MesaDeCentroVitoriana`;
- `SofaVitoriano`.

As duas fábricas seguem o mesmo contrato, que é a interface `FabricaDeMoveis`:

```java
public interface FabricaDeMoveis {

    Cadeira criarCadeira();
    MesaDeCentro criarMesaDeCentro();
    Sofa criarSofa();
}
```

Então a `LojaMoveis` não precisa saber se está recebendo uma fábrica moderna ou vitoriana. Ela só sabe que recebeu alguma coisa capaz de criar cadeira, mesa de centro e sofá.

Na `Principal`, a escolha ainda existe:

```java
LojaMoveis lojaModerna = new LojaMoveis(new FabricaDeMoveisModernos());
LojaMoveis lojaVitoriana = new LojaMoveis(new FabricaDeMoveisVitorianos());

lojaModerna.montarSala();
lojaVitoriana.montarSala();
```

Isso é importante: o padrão não faz a escolha sumir do sistema. Ele muda o lugar da escolha.

Antes, a escolha estava dentro da loja, misturada com a regra de montar sala. Agora, a aplicação escolhe qual fábrica usar, e a loja apenas usa essa fábrica para montar a sala.

## Percepções sobre a solução

O Abstract Factory fica mais fácil de entender quando a gente pensa em família.

Não estamos falando apenas de criar uma classe ou outra. Estamos falando de criar vários objetos que precisam combinar entre si.

Uma sala moderna deve ter cadeira moderna, sofá moderno e mesa moderna. Uma sala vitoriana deve ter cadeira vitoriana, sofá vitoriano e mesa vitoriana. Se cada objeto for criado solto, fica fácil misturar sem querer.

Foi exatamente isso que aconteceu na Fase 2: a sala vitoriana usava cadeira e sofá vitorianos, mas ainda criava uma mesa moderna. O código até roda, mas a ideia do negócio fica errada.

Com a fábrica abstrata, essa combinação fica mais protegida. Quando eu uso `FabricaDeMoveisVitorianos`, eu não preciso lembrar manualmente quais classes fazem parte da família vitoriana. A própria fábrica já carrega essa decisão.

Uma forma simples de guardar a moral é:

> Quando vários objetos precisam nascer combinando, não deixe quem usa esses objetos montar a combinação na mão.

No dia a dia de dev, isso aparece quando uma classe começa a ficar cheia de `if`, `switch` ou regra espalhada só para decidir qual implementação concreta instanciar.

Por exemplo:

- se o provedor de pagamento for Stripe, cria cliente Stripe, boleto Stripe e recibo Stripe;
- se for Mercado Pago, cria cliente Mercado Pago, boleto Mercado Pago e recibo Mercado Pago;
- se o tema for claro, cria botão claro, input claro e menu claro;
- se o tema for escuro, cria botão escuro, input escuro e menu escuro;
- se o banco for MySQL, cria conexão, comando e transação de MySQL;
- se for PostgreSQL, cria conexão, comando e transação de PostgreSQL.

O padrão ajuda quando essas coisas não são peças aleatórias. Elas pertencem ao mesmo conjunto.

Então a grande sacada não é "criar uma factory porque factory é bonito". A sacada é tirar da regra de negócio a responsabilidade de saber quais classes concretas formam uma família válida.

No nosso exemplo, a `LojaMoveis` não deveria ser especialista em estilos de móveis. Ela deveria saber montar uma sala.

Quem é especialista em móveis modernos é a `FabricaDeMoveisModernos`.

Quem é especialista em móveis vitorianos é a `FabricaDeMoveisVitorianos`.

## Aplicabilidade

Esse padrão faz sentido quando eu tenho famílias de objetos relacionados.

Não basta ter várias classes. O ponto é essas classes precisarem ser usadas juntas de forma coerente.

Eu olharia para Abstract Factory quando:

- existem várias versões dos mesmos tipos de objeto;
- essas versões precisam combinar entre si;
- o código cliente não deveria conhecer as classes concretas;
- adicionar uma nova família parece provável;
- a classe principal está acumulando `if` para decidir o que instanciar;
- um erro de combinação pode gerar comportamento estranho mesmo com o código compilando.

No exemplo dos móveis, os tipos de objeto são:

- cadeira;
- mesa de centro;
- sofá.

As famílias são:

- moderna;
- vitoriana.

Se amanhã entrar uma família `Industrial`, a loja não deveria precisar ganhar mais um `if`. O caminho seria criar uma nova fábrica:

```java
public class FabricaDeMoveisIndustriais implements FabricaDeMoveis {
    // cria cadeira industrial
    // cria mesa industrial
    // cria sofa industrial
}
```

Depois disso, a loja continuaria funcionando do mesmo jeito, porque ela depende da interface `FabricaDeMoveis`, não das classes concretas.

Essa é a correlação com o mundo real: o código que executa a regra principal deve depender de contratos mais estáveis, e não de cada variação concreta que pode aparecer no futuro.

## Quando não usar

Eu evitaria usar Abstract Factory quando o problema ainda não tem família de objetos.

Se eu só preciso criar uma única classe, talvez Factory Method ou até um `new` direto resolva melhor.

Também pode ser exagero usar esse padrão quando existem poucas variações e elas dificilmente vão crescer. Nesse caso, criar interface, fábrica abstrata e fábricas concretas pode deixar o código mais espalhado do que necessário.

Outro ponto: se os objetos não precisam combinar entre si, talvez não seja Abstract Factory.

Por exemplo, se eu tenho apenas uma escolha isolada entre `EmailNotification` e `SmsNotification`, isso parece mais uma factory simples ou Factory Method. Agora, se cada canal precisa criar remetente, template, rastreador e configuração própria, aí começa a parecer uma família de objetos.

Então a pergunta boa é:

> Eu estou criando um objeto, ou estou criando um conjunto de objetos que precisam nascer juntos e combinando?

Se for só um objeto, talvez Abstract Factory seja demais.

Se for um conjunto de objetos relacionados, aí o padrão começa a fazer bastante sentido.

No fim, a moral prática é: use Abstract Factory quando você quer proteger o código de combinações erradas e tirar da classe cliente o peso de conhecer todas as implementações concretas.
