# Graph Score

Graph Score é um projeto em Java que demonstra um sistema de recomendação de filmes baseado em grafo. A aplicação monta um grafo de filmes, conecta títulos por atributos como gênero, diretor e elenco, e usa um motor de recomendação para sugerir filmes com base no perfil de um usuário.

## Objetivo

O projeto foi criado para explorar conceitos de:

- estruturas de grafos;
- recomendação baseada em conexões;
- pesos e multiplicadores de relacionamento;
- visualização simples com Swing.

## Funcionalidades

- Criação de um grafo com filmes e conexões entre eles;
- Cálculo de pontuação para recomendações;
- Perfil de usuário com filmes curtidos e diretores favoritos;
- Exibição das recomendações no console;
- Visualização gráfica do grafo em uma janela Swing.

## Tecnologias

- Java 21
- Maven
- Swing

## Estrutura do projeto

- src/main/java/com/lucasbarros/Main.java: ponto de entrada da aplicação
- src/main/java/com/lucasbarros/graph/: implementação do grafo e das conexões
- src/main/java/com/lucasbarros/model/: modelos de domínio (filme, usuário, elenco)
- src/main/java/com/lucasbarros/recommendation/: motor de recomendação
- src/main/java/com/lucasbarros/visual/: painel gráfico para visualização

## Como executar

1. Certifique-se de ter o Java 21 e o Maven instalados.
2. Na raiz do projeto, execute:

```bash
mvn compile
java -cp target/classes com.lucasbarros.Main
```

Ao executar, o programa:

- imprime recomendações no terminal;
- abre uma janela com o gráfico dos filmes.

## Exemplo de comportamento

A aplicação cria um perfil de usuário com filmes curtidos e calcula recomendações com base nos relacionamentos entre filmes, como:

- filmes do mesmo gênero;
- filmes com atores em comum;
- filmes do mesmo diretor;
- preferência por diretores favoritos.

## Próximos passos

Possíveis melhorias para o projeto:

- adicionar persistência de filmes e usuários;
- permitir carregar dados de um arquivo externo;
- melhorar a interface gráfica;
- implementar mais tipos de conexão e pesos refinados.
