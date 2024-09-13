# Exemplo de Projeto JavaSE com JDBC

Projeto JavaSE do tipo [Maven](https://maven.apache.org) que utiliza JDBC para conectar a um banco de dados [Apache H2](http://h2database.com) em memória, que é criado automaticamente quando a aplicação inicia.

O projeto mostra como usar recursos básicos da JDBC para acesso a um banco de dados relacional qualquer.
Foi usado o banco H2 por não exigir instalação de um servidor de banco de dados na sua máquina.
Isto permite baixar o projeto e executar em um IDE como [IntelliJ](https://www.jetbrains.com/idea/) ou editor como [VSCode](https://code.visualstudio.com) sem precisar instalar nada manualmente.

O Driver JDBC para o Apache H2 é adicionado como uma dependência no [pom.xml](pom.xml).

## Executando o Projeto

Após abrir o projeto em um editor de código ou IDE com suporte a Java (como os mencionados), basta executar a classe [ExemploJdbc.java](src/main/java/exemplojdbc/ExemploJdbc.java).

Ela vai conectar a um banco H2 em memória (que é recriado cada vez que a aplicação é iniciada), executar o script [schema.sql](src/main/resources/schema.sql) para criar as tabelas e popular o banco, e depois realiza algumas consultas ao banco.