# Singleton

Também conhecido como Carta Única.

## Problema prático

Neste estudo, a história é uma aplicação corporativa simples.

Essa aplicação tem algumas configurações que precisam ser usadas por várias partes do sistema:

- ambiente atual;
- URL do banco de dados;
- remetente padrão de e-mail;
- formato padrão dos relatórios.

Por exemplo:

```text
ambiente = "produção"
urlBancoDados = "jdbc:mysql://localhost:3306/app"
remetenteEmail = "sistema@empresa.com"
formatoRelatorio = "PDF"
```

Até aqui parece uma coisa boba.

Mas pensa no uso real.

O `ServicoEmail` precisa saber qual remetente usar.

O `ServicoRelatorio` precisa saber qual formato usar.

O `ServicoAuditoria` precisa saber em qual ambiente está rodando.

Ou seja, várias partes do sistema precisam consultar a mesma configuração.

E aí começa a pergunta:

> Onde essa configuração deveria morar?

## Problema - Fase 1: cada um cria sua própria configuração

Na pasta `Problema - Fase 1`, cada serviço cria uma nova instância de `ConfiguracaoSistema`.

Por exemplo, o `ServicoEmail` faz:

```java
private ConfiguracaoSistema configuracaoSistema = new ConfiguracaoSistema(
        "produção",
        "jdbc:mysql://localhost:3306/app",
        "sistema@empresa.com",
        "PDF");
```

O `ServicoRelatorio` faz quase a mesma coisa:

```java
private ConfiguracaoSistema configuracaoSistema = new ConfiguracaoSistema(
        "produção",
        "jdbc:mysql://localhost:3306/app",
        "sistema@empresa.com",
        "PDF");
```

E o `ServicoAuditoria` também.

Num primeiro momento, isso funciona.

Cada serviço tem uma configuração.

Só que aí vem o incômodo:

> Se a configuração é a mesma para a aplicação inteira, por que cada classe está criando a sua?

O problema não é só repetir código.

O problema é que cada `new ConfiguracaoSistema(...)` cria um objeto diferente.

Então o sistema pode ficar assim:

```text
ServicoEmail
    configuracaoSistema objeto #1

ServicoRelatorio
    configuracaoSistema objeto #2

ServicoAuditoria
    configuracaoSistema objeto #3
```

Mesmo que os valores sejam iguais, são objetos separados.

E se alguém mudar um valor em um lugar e esquecer em outro?

Por exemplo:

```java
"PDF"
```

em uma classe, mas:

```java
"XLSX"
```

em outra.

O código compila.

Mas a aplicação começa a ter configurações diferentes dependendo de quem está usando.

Isso é estranho porque configuração global deveria ter uma fonte única de verdade.

## Percepções sobre a Fase 1

O problema da Fase 1 é:

> existem várias cópias de uma informação que deveria ser única.

É como se cada setor da empresa tivesse uma plaquinha diferente dizendo qual é o endereço oficial da matriz.

Se todo mundo escreveu o mesmo endereço, beleza.

Mas se um setor atualizar e o outro não, acabou a confiança.

No código, isso aparece quando a gente vê o mesmo `new ConfiguracaoSistema(...)` espalhado em várias classes.

Aí a dúvida natural é:

> E se eu colocar isso em algo estático?

Essa é uma tentativa comum.

E ela aparece na Fase 2.

## Problema - Fase 2: usando coisas estáticas

Na pasta `Problema - Fase 2`, a tentativa é criar uma classe `ConfiguracaoGlobal`.

A ideia parece boa:

```java
public class ConfiguracaoGlobal {

    public static String ambiente = "produção";
    public static String urlBancoDados = "jdbc:mysql://localhost:3306/app";
    public static String remetenteEmail = "sistema@empresa.com";
    public static String formatoRelatorio = "PDF";
}
```

Agora o `ServicoEmail` não precisa mais criar configuração com `new`.

Ele pode acessar direto:

```java
ConfiguracaoGlobal.remetenteEmail
```

O `ServicoRelatorio` pode acessar:

```java
ConfiguracaoGlobal.formatoRelatorio
```

E o `ServicoAuditoria` pode acessar:

```java
ConfiguracaoGlobal.ambiente
```

Aqui a gente ganhou uma coisa:

> agora existe um ponto global de acesso.

Mas a gente ganhou do jeito perigoso.

Porque esses campos são públicos e estáticos.

Então qualquer lugar do sistema consegue fazer:

```java
ConfiguracaoGlobal.ambiente = "teste";
```

ou:

```java
ConfiguracaoGlobal.formatoRelatorio = "TXT";
```

E pronto.

A configuração global foi alterada de qualquer lugar.

Essa é a parte traiçoeira do `static`.

Ele parece resolver o problema da repetição, mas pode abrir uma porta para bagunça global.

## Debugando mentalmente a Fase 2

Na `Principal`, o fluxo da Fase 2 é assim:

```java
ServicoEmail servicoEmail = new ServicoEmail();
ServicoRelatorio servicoRelatorio = new ServicoRelatorio();
ServicoAuditoria servicoAuditoria = new ServicoAuditoria();
```

Até aqui, tudo certo.

Depois:

```java
servicoEmail.enviarEmailBoasVindas();
servicoRelatorio.gerarRelatorioMensal();
servicoAuditoria.registrarEvento("Usuário acessou o sistema");
```

Todos consultam a mesma classe estática `ConfiguracaoGlobal`.

Mentalmente:

```text
ServicoEmail ------+
                   |
ServicoRelatorio --+--> ConfiguracaoGlobal
                   |
ServicoAuditoria --+
```

O problema aparece quando algum serviço altera a configuração:

```java
servicoRelatorio.alterarFormatoRelatorioParaTeste();
```

Esse método faz:

```java
ConfiguracaoGlobal.formatoRelatorio = "TXT";
```

Agora o valor global mudou.

Qualquer outro lugar que consultar `ConfiguracaoGlobal.formatoRelatorio` vai enxergar `"TXT"`.

Mesmo que isso tenha sido feito só para um teste rápido.

É como deixar a chave do painel elétrico da empresa em cima da mesa.

Qualquer um pode mexer.

## Onde o Singleton começa a fazer sentido

O Singleton entra quando eu tenho duas necessidades ao mesmo tempo:

1. quero uma única instância;
2. quero um ponto global de acesso controlado.

Na Fase 1, eu tinha várias instâncias.

Na Fase 2, eu tinha acesso global, mas sem controle direito.

O Singleton tenta juntar as duas ideias:

```text
uma única instância
+
acesso global controlado
```

Em vez de deixar todo mundo criar:

```java
new ConfiguracaoSistema(...)
```

e em vez de deixar todo mundo mexer em:

```java
ConfiguracaoGlobal.formatoRelatorio = "TXT";
```

a classe passa a controlar sua própria instância.

A ideia final será algo nessa direção:

```java
ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstancia();
```

Repara na diferença de intenção.

Com `new`, eu estou dizendo:

```text
crie uma configuração nova
```

Com `getInstancia`, eu estou dizendo:

```text
me dê a configuração única do sistema
```

Esse é um clique importante.

Singleton não é só "colocar static".

Singleton é controlar quem cria e como acessa a instância.

## Por que não é só deixar tudo static?

Essa dúvida é muito importante.

Porque dá para pensar:

> Se eu posso usar `ConfiguracaoGlobal.ambiente`, por que preciso de Singleton?

O ponto é que `static` puro não cria objeto.

Ele deixa dados e métodos presos na classe.

Isso pode ser útil em constantes ou funções utilitárias.

Mas quando a coisa representa um objeto real da aplicação, com estado e comportamento, `static` começa a ficar limitado.

Uma configuração do sistema pode ter comportamento:

```java
exibirResumo()
getAmbiente()
getUrlBancoDados()
getRemetenteEmail()
getFormatoRelatorio()
```

Ela também pode controlar a própria criação.

Com Singleton, a classe pode esconder o construtor e dizer:

```text
ninguém cria configuração por fora
```

Só a própria classe pode criar sua instância.

Isso é diferente de jogar variáveis públicas estáticas para todo mundo mexer.

## Solução proposta

Na pasta `Solução`, a `ConfiguracaoGlobal` deixa de ser uma classe com campos públicos estáticos.

Antes, na Fase 2, qualquer classe conseguia fazer isso:

```java
ConfiguracaoGlobal.formatoRelatorio = "TXT";
```

Isso era perigoso porque o estado global ficava exposto.

Na solução, a classe passa a controlar a própria instância:

```java
public class ConfiguracaoGlobal {

    private static ConfiguracaoGlobal instancia;

    private String ambiente;
    private String urlBancoDados;
    private String remetenteEmail;
    private String formatoRelatorio;
}
```

Aqui tem duas coisas importantes.

Primeiro:

```java
private static ConfiguracaoGlobal instancia;
```

Esse atributo pertence à classe, não a um objeto específico.

Por isso ele é `static`.

Ele é o lugar onde a classe guarda a única instância dela mesma.

Segundo:

```java
private String ambiente;
private String urlBancoDados;
private String remetenteEmail;
private String formatoRelatorio;
```

Esses dados agora pertencem ao objeto.

Eles não são mais variáveis públicas soltas na classe.

Isso já muda bastante a ideia.

Antes:

```text
ConfiguracaoGlobal.formatoRelatorio
```

Agora:

```text
configuracaoGlobal.getFormatoRelatorio()
```

Parece detalhe, mas não é.

No primeiro caso, estou pegando um campo global solto.

No segundo caso, estou conversando com um objeto único.

## O construtor privado

Na solução, o construtor ficou assim:

```java
private ConfiguracaoGlobal() {
    this.ambiente = "produção";
    this.urlBancoDados = "jdbc:mysql://localhost:3306/app";
    this.remetenteEmail = "sistema@empresa.com";
    this.formatoRelatorio = "PDF";
}
```

Esse `private` é uma das partes mais importantes do Singleton.

Ele impede isso:

```java
new ConfiguracaoGlobal();
```

Se outra classe tentar criar a configuração com `new`, o Java não deixa.

Por quê?

Porque o construtor privado só pode ser chamado dentro da própria classe.

Então a `ConfiguracaoGlobal` está dizendo:

> ninguém de fora cria outra configuração global.

Isso resolve a dor da Fase 1.

Na Fase 1, cada serviço fazia seu próprio `new`.

Na solução, isso deixa de ser possível.

## O método getInstancia

Como o construtor é privado, surge uma pergunta natural:

> se eu não posso usar `new`, como eu pego a configuração?

Pelo método:

```java
public static ConfiguracaoGlobal getInstancia() {
    if (instancia == null) {
        instancia = new ConfiguracaoGlobal();
    }

    return instancia;
}
```

Esse método é o ponto global de acesso.

Ele é `static` porque eu preciso chamá-lo direto pela classe:

```java
ConfiguracaoGlobal.getInstancia();
```

Se ele não fosse `static`, eu precisaria de um objeto para chamar o método.

Só que justamente estou tentando obter esse objeto.

Então faria pouco sentido.

O fluxo do método é:

```text
alguém pediu a instância
        ↓
instancia está null?
        ↓
sim: cria uma nova ConfiguracaoGlobal
        ↓
não: usa a que já existe
        ↓
retorna a instância
```

Na primeira chamada:

```java
ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();
```

`instancia` ainda está `null`.

Então acontece:

```java
instancia = new ConfiguracaoGlobal();
```

Na segunda chamada:

```java
ConfiguracaoGlobal outraReferencia = ConfiguracaoGlobal.getInstancia();
```

`instancia` já não está mais `null`.

Então o método apenas retorna o mesmo objeto.

Não cria outro.

Essa é a essência do Singleton.

## Debugando mentalmente a Principal

Na `Principal`, agora aparece:

```java
ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();
```

Essa linha não quer dizer:

```text
crie uma configuração nova
```

Ela quer dizer:

```text
me dê a configuração única do sistema
```

Depois:

```java
configuracaoGlobal.exibirResumo();
```

Aqui a `Principal` usa o objeto único para exibir a configuração.

Mais abaixo, os serviços também usam a mesma instância.

No `ServicoEmail`:

```java
ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

System.out.println("Remetente: " + configuracaoGlobal.getRemetenteEmail());
```

No `ServicoRelatorio`:

```java
ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

System.out.println("Formato: " + configuracaoGlobal.getFormatoRelatorio());
```

No `ServicoAuditoria`:

```java
ConfiguracaoGlobal configuracaoGlobal = ConfiguracaoGlobal.getInstancia();

System.out.println("Ambiente: " + configuracaoGlobal.getAmbiente());
System.out.println("Banco: " + configuracaoGlobal.getUrlBancoDados());
```

Repara no desenho:

```text
Principal --------+
                  |
ServicoEmail -----+
                  +--> ConfiguracaoGlobal.getInstancia() --> mesma instância
ServicoRelatorio -+
                  |
ServicoAuditoria -+
```

Todo mundo chama `getInstancia()`.

Mas todo mundo recebe o mesmo objeto.

Esse é o ganho.

## Comparando Fase 2 com a Solução

Na Fase 2, o acesso era assim:

```java
ConfiguracaoGlobal.formatoRelatorio
```

E a alteração era assim:

```java
ConfiguracaoGlobal.formatoRelatorio = "TXT";
```

Isso é direto demais.

Qualquer lugar consegue ler e escrever.

Na solução, os dados ficaram privados:

```java
private String formatoRelatorio;
```

E a leitura passa por método:

```java
public String getFormatoRelatorio() {
    return formatoRelatorio;
}
```

Então o serviço faz:

```java
configuracaoGlobal.getFormatoRelatorio();
```

Isso melhora o encapsulamento.

Agora a classe controla como seus dados são acessados.

Neste estudo, ainda deixei um método para alterar o formato:

```java
public void alterarFormatoRelatorioParaTeste() {
    this.formatoRelatorio = "TXT";
}
```

Por quê?

Para mostrar que, mesmo quando existe alteração, ela passa por comportamento da própria classe.

Antes:

```java
ConfiguracaoGlobal.formatoRelatorio = "TXT";
```

Depois:

```java
configuracaoGlobal.alterarFormatoRelatorioParaTeste();
```

No primeiro caso, a classe de fora mete a mão no campo.

No segundo caso, a classe de fora pede para a própria configuração fazer a alteração.

É uma diferença básica de orientação a objetos:

> objeto não é só um pacote de dados; objeto também controla comportamento.

## Dúvidas que parecem bobas, mas não são

### Singleton é só uma variável global bonita?

Não deveria ser.

Ele tem semelhança com variável global porque todo mundo consegue acessar.

Mas a ideia é ter mais controle.

Uma variável global pública pode ser sobrescrita de qualquer lugar.

O Singleton tenta proteger a criação da instância.

### Por que o construtor precisa ser privado?

Porque se o construtor continuar público, qualquer classe ainda consegue fazer:

```java
new ConfiguracaoSistema(...)
```

Aí acabou o "único".

Construtor privado quer dizer:

> ninguém de fora cria essa classe diretamente.

Só a própria classe pode criar a própria instância.

### Por que o método de acesso é static?

Porque, antes de existir um objeto, eu preciso de algum jeito de pedir esse objeto.

Se eu precisasse de um objeto para chamar o método que cria o objeto, eu cairia num ciclo estranho.

Então o acesso costuma ser:

```java
ConfiguracaoSistema.getInstancia()
```

Esse método pertence à classe.

Ele verifica se a instância já existe.

Se não existe, cria.

Se já existe, retorna a mesma.

### Singleton sempre é bom?

Não.

Esse padrão tem fama polêmica.

Ele pode facilitar acesso global demais.

Se qualquer classe do sistema chama `ConfiguracaoSistema.getInstancia()` a qualquer momento, o acoplamento pode ficar escondido.

Também pode atrapalhar testes, porque fica mais difícil trocar a instância por uma falsa.

Então Singleton deve ser usado com cuidado.

Ele faz sentido quando a regra de negócio realmente pede uma instância única.

Não é para transformar qualquer classe importante em Singleton.

### Qual é a diferença entre uma constante static final e Singleton?

Uma constante é só um valor fixo:

```java
public static final String SISTEMA = "Sistema Financeiro";
```

Singleton é um objeto único.

Ele pode ter vários dados e comportamento.

Se eu só preciso de valores fixos, talvez constantes resolvam.

Se eu preciso de um objeto único que centraliza uma responsabilidade, aí Singleton pode começar a fazer sentido.

## Aplicabilidade

Eu olharia para Singleton quando:

- uma classe realmente deve ter uma única instância;
- várias partes do sistema precisam acessar essa mesma instância;
- criar várias instâncias poderia causar inconsistência;
- existe um recurso compartilhado que precisa ser controlado;
- a criação do objeto deve ficar protegida;
- `static` puro começou a parecer uma gambiarra global;
- o objeto representa uma fonte única de verdade.

Exemplos comuns:

- configuração da aplicação;
- gerenciador de conexão;
- logger central;
- cache global;
- registrador de plugins;
- fila compartilhada.

Mesmo assim, sempre vale a pergunta:

> Eu preciso mesmo de uma única instância, ou só estou procurando um jeito fácil de acessar algo de qualquer lugar?

Se for só preguiça de passar dependência, cuidado.

## Quando não usar

Eu evitaria Singleton quando:

- a classe não precisa ser única;
- eu só quero evitar passar objeto por parâmetro;
- o estado global pode atrapalhar testes;
- a aplicação pode precisar de múltiplas configurações;
- o objeto muda muito dependendo do contexto;
- a instância única esconderia dependências importantes.

Por exemplo, se eu tenho dois clientes conectados em bancos diferentes, talvez não faça sentido uma única configuração global.

Se eu tenho testes rodando em paralelo com configurações diferentes, Singleton também pode virar dor.

## Moral prática

Singleton não é "usar static e pronto".

Singleton é:

```text
controlar a criação
garantir uma única instância
oferecer um ponto global de acesso
```

No nosso exemplo:

- Fase 1 mostra o problema de criar várias configurações iguais com `new`;
- Fase 2 mostra a tentativa com `static`, que dá acesso global mas deixa o estado exposto;
- a Solução parte da Fase 2 para aplicar Singleton e transformar essa configuração em uma instância única controlada.

Uma frase curta para guardar:

> Singleton é quando a classe diz: "ninguém cria outra de mim; se precisar, pega a única instância comigo".
