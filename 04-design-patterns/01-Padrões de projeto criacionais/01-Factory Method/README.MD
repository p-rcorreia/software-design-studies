# Factory Method

Tambem conhecido como Metodo Fabrica ou Construtor Virtual.

## Problema pratico

Na pasta `Problema - Fase 1`, você pode observar o cenário de uma empresa de logística que inicialmente trabalha apenas com transporte viário, utilizando um caminhão para fazer entregas de caixas por terra.

Até aqui, tudo bem...

Mas vamos supor que esse sistema fique bem popular e as pessoas comecem a pedir pra gente implementar também o gerenciamento de entregas marítimas, utilizando um navio.

É nesse ponto que começam a surgir alguns desafios. Quais? Por que?

Até então, a aplicação inteira está diretamente ligada à classe `Caminhao`. Qualquer parte da aplicação que precise usar o meio de transporte viário faz uma chamada para `Caminhao`.

Se seguirmos essa lógica, para implementar uma solução onde também seja possível chamar `Navio`, ou até substituir chamadas de `Caminhao` por `Navio`, vamos ter que mexer diretamente nas partes do código que antes dependiam de `Caminhao`.

Em exemplos pequenos assim, é claro que parece simples fazer essa troca ou adicionar um novo meio de transporte. Mas devemos lembrar que, em um sistema real, cada objeto, como `Caminhao` e `Navio`, pode ter vários atributos, métodos, comportamentos, particularidades, exceções e exigências.

Isso poderia obrigar a gente a adaptar várias partes do sistema para se adequar ao novo objeto. E um ponto muito importante é que, diferente deste exemplo pequeno, um sistema costuma ser grande e complexo.

Nesse caso, onde gostaríamos de adicionar o meio de transporte marítimo, você verá na pasta da continuação do problema (`Problema - Fase 2`) a implementação necessária sem a utilização do Factory Method.

FASE 2

Então vamos às percepções do projeto nessa segunda fase

Você pode ver que criamos a classe `Navio` e passamos a instanciar tanto `Caminhao` quanto `Navio` diretamente na aplicação. Perceba que, mesmo funcionando, a `Principal` agora precisa conhecer todas as classes concretas de transporte que podem ser usadas.

O problema não é apenas "ter duas classes". O problema é que a aplicação começa a conhecer diretamente cada classe concreta que pode realizar uma entrega.

Na Fase 1, isso quase não incomoda, porque existe apenas `Caminhao`. Mas, na Fase 2, quando adicionamos `Navio`, a `Principal` passa a precisar conhecer mais de uma opção de transporte.

Se no futuro surgirem novas modalidades, como `Aviao` ou `Trem`, essa mesma parte do código teria que ser alterada novamente. Ou seja, para adicionar uma nova possibilidade ao sistema, precisamos mexer em um código que já estava funcionando.

Além disso, se outras partes da aplicação também precisarem criar transportes, essa lógica pode começar a se repetir em vários lugares. Com o tempo, a manutenção fica mais arriscada, porque cada nova modalidade exige mudanças espalhadas pelo sistema.


## Percepcoes sobre o problema

Registrar aqui o que ficou evidente ao observar o problema antes de aplicar o padrao.

## Solucao proposta

Explicar aqui como o Factory Method resolve ou melhora o problema.

## Percepcoes sobre a solucao

Registrar aqui o que mudou depois da aplicacao do padrao.

## Aplicabilidade

Listar aqui quando esse padrao faz sentido no dia a dia.

## Quando nao usar

Listar aqui situacoes em que o padrao pode adicionar complexidade sem necessidade.
