# Duvida

Oie professor,

Seguindo suas orientações, conseguimos:
- Resolver aquele erro que estava dando quanto à um array não inicializado;
- Tirei as chamadas de métodos do Server que estavam sendo feitas pelo Client;
- Foi retirados as chamadas de métodos da classe Match que estavam sendo feitas pelo Server;
- Na thread Attend foi instanciado um objeto Match, o qual é chamado para executar as requisições do Client feitas através de uma comunicação via Socket, estando estas chamadas dentro do método Run();
- Foi enviado do Client para a thread Attend o objeto Player e suas escolhas, como dificuldade do bot e modo de jogo;



Busquei aplicar tudo o que foi passado, porém acredito que durante a implementação da comunicação entre o Client e a thread Attend eu devo ter feito algo errado, o qual não fui capaz de identificar hoje até agora, tentei bastante porém não obtive êxito em resolver as seguintes mensagens de erro:

* Erros apresentados para o cliente:
Cannot invoke "util.Card.getSymbol()" because "mesa" is null
Cannot invoke "java.net.Socket.getOutputStream()" because "socket" is null
Cannot invoke "java.io.ObjectInputStream.readObject()" because "this.input" is null
Cannot invoke "java.io.ObjectOutputStream.writeObject(Object)" because "this.output" is null

* Erros apresentados para o server:
Cannot invoke "util.Requisicao.getId_Requisicao()" because "requisicao" is null

O programa para de funcionar antes do jogo em si começar, tentei ao máximo solucionar esses problemar sem ter que pedir ajuda pra você de novo com outra coisa professor, mas não consegui


Atenciosamente, 
Alison de Almeida Sales, Giovanna Paola Lunetta Crepaldi Matias e etc.
