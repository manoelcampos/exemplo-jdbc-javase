# Exemplo de Projeto JavaSE com JDBC

Projeto JavaSE do tipo [Maven](https://maven.apache.org) que utiliza JDBC para conectar a um banco de dados [Apache H2](http://h2database.com) em memória, que é criado automaticamente quando a aplicação inicia.

O projeto mostra como usar recursos básicos da JDBC para acesso a um banco de dados relacional qualquer.
Foi usado o banco H2 por não exigir instalação de um servidor de banco de dados na sua máquina.
Isto permite executar o projeto em um ambiente online como o https://gitpod.io, em um IDE como [IntelliJ](https://www.jetbrains.com/idea/) ou editor como [VSCode](https://code.visualstudio.com) sem precisar instalar nada manualmente (mais detalhes abaixo).

O Driver JDBC para o Apache H2 é adicionado como uma dependência no [pom.xml](pom.xml).

## Executando o projeto

Existem duas formas de abrir e executar este projeto: usando um ambiente online como o GitPod ou executando localmente em um editor de código ou IDE na sua máquina.

Independente de qual seja sua escolha, após abrir o projeto, basta executar a classe [JdbcExemplo1.java](src/main/java/exemplojdbc/JdbcExemplo1.java). Ela vai conectar a um banco H2 em memória (que é recriado cada vez que a aplicação é iniciada), executar o script [schema.sql](src/main/resources/schema.sql) para criar as tabelas e popular o banco, e depois realiza algumas consultas ao banco.

### Executando o projeto remotamente no GitPod
[![](https://gitpod.io/button/open-in-gitpod.svg)](https://gitpod.io/#https://github.com/manoelcampos/exemplo-jdbc-javase)

> **Warning**
> - O gitpod.io não funciona se você tiver a extensão do tradutor habilitada no seu navegador.
> - O endereço do repositório no GitHub a ser incluído no endereço do gitpod deve obrigatoriamente ser https

Clique no botão acima para abrir o projeto no GitPod contendo um VS Code pronto pra ser usado direto do navegador.

