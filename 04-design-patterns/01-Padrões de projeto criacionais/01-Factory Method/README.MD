# Factory Method

Tambem conhecido como Metodo Fabrica ou Construtor Virtual.

## Problema pratico

Na pasta `Problema - Fase 1`, você pode observar o cenário de uma empresa de logística que inicialmente trabalha apenas com transporte viário, utilizando um caminhão para fazer entregas de caixas por terra.

Até aqui, tudo bem...

Mas vamos supor que esse sistema fique bem popular e as pessoas comecem a pedir pra gente implementar também o gerenciamento de entregas marítimas, utilizando um navio.

É nesse ponto que começam a surgir alguns desafios. Quais? Por que?

Até então, a `EmpresaLogistica` está diretamente ligada à classe `Caminhao`. Ela até tem um método que representa bem a regra do negócio, o `planejarEntrega`, mas dentro dele a criação do transporte acontece de forma concreta.

Se seguirmos essa lógica, para implementar uma solução onde também seja possível chamar `Navio`, ou até substituir chamadas de `Caminhao` por `Navio`, vamos ter que mexer diretamente na lógica da empresa que antes dependia de `Caminhao`.

Em exemplos pequenos assim, é claro que parece simples fazer essa troca ou adicionar um novo meio de transporte. Mas devemos lembrar que, em um sistema real, cada objeto, como `Caminhao` e `Navio`, pode ter vários atributos, métodos, comportamentos, particularidades, exceções e exigências.

Isso poderia obrigar a gente a adaptar várias partes do sistema para se adequar ao novo objeto. E um ponto muito importante é que, diferente deste exemplo pequeno, um sistema costuma ser grande e complexo.

Nesse caso, onde gostaríamos de adicionar o meio de transporte marítimo, você verá na pasta da continuação do problema (`Problema - Fase 2`) a implementação necessária sem a utilização do Factory Method.

FASE 2

Então vamos às percepções do projeto nessa segunda fase

Você pode ver que criamos a classe `Navio` e alteramos a `EmpresaLogistica` para decidir qual transporte deve ser criado. Perceba que, mesmo funcionando, o método `planejarEntrega` agora precisa conhecer todas as classes concretas de transporte que podem ser usadas.

## Percepcoes sobre o problema

O problema não é apenas "ter duas classes". O problema é que a classe responsável por planejar a entrega começa a conhecer diretamente cada classe concreta que pode realizar uma entrega.

Na Fase 1, isso quase não incomoda, porque existe apenas `Caminhao`. Mas, na Fase 2, quando adicionamos `Navio`, a `EmpresaLogistica` passa a precisar conhecer mais de uma opção de transporte e decidir qual delas deve ser usada.

Se no futuro surgirem novas modalidades, como `Aviao` ou `Trem`, esse mesmo método teria que ser alterado novamente. Ou seja, para adicionar uma nova possibilidade ao sistema, precisamos mexer em um código que já estava funcionando.

Além disso, o `planejarEntrega` começa a misturar duas ideias: planejar a entrega e escolher qual transporte concreto será criado. Com o tempo, a manutenção fica mais arriscada, porque cada nova modalidade pode aumentar as condições dentro dessa mesma classe.

## Solucao proposta

Na solução, a primeira mudança é parar de tratar `Caminhao` e `Navio` como coisas totalmente soltas na lógica da empresa. Os dois passam a representar uma mesma ideia: um `Transporte`.

Com isso, a `EmpresaLogistica` não precisa mais trabalhar diretamente com `Caminhao` ou `Navio`. Ela continua tendo o método `planejarEntrega`, porque essa é a regra que faz sentido para uma empresa de logística. Mas a parte que muda, que é a criação do transporte, foi separada em um método próprio: `criarTransporte`.

Esse método é o Factory Method.

A `EmpresaLogistica` sabe que precisa criar algum `Transporte`, mas não decide qual classe concreta será usada. Quem faz essa escolha são as classes filhas:

- `LogisticaViaria` cria um `Caminhao`.
- `LogisticaMaritima` cria um `Navio`.

Assim, o fluxo principal continua parecido: planejar uma entrega e chamar o método `entregar`. A diferença é que a criação do objeto concreto saiu de dentro da lógica geral da empresa e foi para classes específicas de cada tipo de logística.

Um detalhe importante é que a `EmpresaLogistica` não virou uma fábrica no sentido de existir apenas para criar objetos. Ela continua sendo a classe que representa o processo de planejar uma entrega. O Factory Method aparece só no ponto variável desse processo: a criação do transporte.

Também é por isso que o `Transporte` precisa existir como uma ideia comum. A `EmpresaLogistica` não precisa saber se recebeu um `Caminhao` ou um `Navio`; ela só precisa saber que recebeu algo capaz de executar `entregar`.

Na `Principal`, ainda existe uma escolha:

- usar `LogisticaViaria`;
- usar `LogisticaMaritima`.

Essa escolha não desaparece completamente. O que muda é o nível da decisão. A aplicação escolhe qual tipo de logística quer usar, mas não precisa mais criar diretamente o transporte concreto usado por dentro dela.

## Percepcoes sobre a solucao

O que ficou mais claro para mim é que o Factory Method não começa com a ideia de "criar uma factory" separada. Ele começa quando uma classe tem um processo que faz sentido, mas uma parte desse processo depende de qual objeto concreto precisa ser criado.

No nosso caso, a `EmpresaLogistica` precisa planejar uma entrega. Esse comportamento continua nela. O que ela não precisa mais saber é se a entrega será feita por `Caminhao` ou por `Navio`.

Antes, para adicionar um novo transporte, eu teria que voltar no método `planejarEntrega` e colocar mais uma condição. Agora, para adicionar uma nova forma de logística, eu posso criar uma nova classe filha e implementar o método `criarTransporte`.

Isso não elimina toda mudança do sistema, mas muda o lugar da mudança. Em vez de mexer em uma regra que já existia e já funcionava, eu adiciono uma nova variação do comportamento.

Outra coisa importante é que o método fábrica geralmente cria um objeto com `new`, mas a ideia principal não é simplesmente "dar new em outro lugar". A ideia é controlar a criação por meio de um método. Esse método pode criar uma instância nova, mas também poderia reaproveitar alguma instância já existente se isso fizesse sentido para o problema.

Por exemplo: em um sistema real, `criarTransporte` poderia buscar um caminhão já cadastrado em uma frota, em vez de criar um caminhão novo toda vez. O método fábrica continuaria escondendo esse detalhe de quem apenas quer planejar a entrega.

Nesse exemplo, criar um novo `Caminhao` ou um novo `Navio` é suficiente. O ponto do padrão é que quem usa a `EmpresaLogistica` não precisa saber como esse `Transporte` foi obtido.

## Aplicabilidade

Esse padrão faz sentido quando uma classe tem um processo importante, mas uma parte desse processo depende de qual objeto concreto será usado.

Também faz sentido quando eu percebo que estou colocando vários `if` ou `switch` para escolher qual classe instanciar, principalmente quando essa escolha tende a crescer com o tempo.

Outro caso é quando eu quero que o código principal trabalhe com uma ideia mais geral, como `Transporte`, e deixe as variações concretas, como `Caminhao` e `Navio`, em classes específicas.

No dia a dia, eu olharia para esse padrão quando:

- a criação de objetos começa a se repetir;
- uma classe precisa criar objetos, mas não deveria conhecer todos os tipos concretos;
- novos tipos podem surgir com frequência;
- existe um fluxo comum, mas uma etapa desse fluxo varia conforme a especialização.

## Quando nao usar

Eu evitaria usar esse padrão quando o problema ainda é muito simples e não existe uma variação real na criação dos objetos.

Se o sistema só precisa criar uma classe concreta e não existe sinal de que outras variações vão aparecer, aplicar Factory Method pode deixar o código mais espalhado sem necessidade.

Também é bom tomar cuidado para não criar uma classe filha para cada pequena diferença sem pensar se aquilo representa mesmo uma variação importante do processo. No exemplo da logística, `LogisticaViaria` e `LogisticaMaritima` fazem sentido porque representam formas diferentes de operar uma entrega.

Mas se a diferença for pequena demais, talvez o caminho seja outro. Se a logística viária apenas precisa escolher entre `Moto`, `Van` ou `Caminhao` com base no peso da entrega, pode ser mais simples ter uma regra interna ou uma factory simples que recebe esses dados e devolve o transporte adequado.

Nesse caso, eu já não estaria aplicando o Factory Method clássico. Eu não estaria criando um novo tipo de logística; estaria apenas escolhendo um veículo dentro da mesma forma de operar.

O padrão ajuda quando existe uma mudança provável e significativa. Se a mudança é pequena, rara ou muito simples, às vezes um código direto é mais fácil de entender e manter.
