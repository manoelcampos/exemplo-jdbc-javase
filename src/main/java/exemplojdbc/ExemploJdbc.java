package exemplojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Exemplo de conexão a um banco de dados Apache H2 em memória utilizando JDBC.
 * @author Manoel Campos
 */
public class ExemploJdbc {
    /**
     * String de conexão com o banco de dados.
     * O banco de dados utilizado é o Apache H2, que está configurado
     * na string de conexão para ser um banco em memória.
     * Você pode criar o banco em arquivo, alterando a estrutura da String de conexão para
     * jdbc:h2:file:/caminho/nome_do_banco
     * Por exemplo: jdbc:h2:file:./banco_exemplo
     * onde ./ indica o diretório atual do projeto.
     */
    private final String CONNECTION_URL = "jdbc:h2:mem:banco_exemplo";
    private final String USERNAME = "sa";
    private final String PASSWORD = "password";

    public static void main(String[] args) {
        new ExemploJdbc();
    }

    public ExemploJdbc() {
        //carregarDriverJDBC();
        try(final var conn = getConnection()){
            System.out.printf("Conexão com o banco realizada com sucesso: %s%n%n", CONNECTION_URL);
            SQLUtils.runFile(conn, "schema.sql"); // Cria as tabelas e popula o banco

            listarEstados(conn);
            localizarEstado(conn, "PR");
            listarDadosTabela(conn, "produto");
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
    }

    /**
     * Lista dinamicamente os dados de todas as linhas e colunas de uma determinada tabela no banco.
     * @param conn conexão com o banco
     * @param tabela nome da tabela pra listar os dados
     */
    private void listarDadosTabela(final Connection conn, final String tabela) {
        final var sql = "select * from " + tabela;
        //System.out.println(sql);
        try {
            final var statement = conn.createStatement();
            final var result = statement.executeQuery(sql);

            final var metadata = result.getMetaData();
            final int cols = metadata.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                System.out.printf("%-30s | ", metadata.getColumnName(i));
            }
            System.out.println();

            while(result.next()){
                for (int i = 1; i <= cols; i++) {
                    System.out.printf("%-30s | ", result.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Erro na execução da consulta: " + e.getMessage());
        }
    }

    private void localizarEstado(final Connection conn, final String uf) {
        try{
            //var sql = "select * from estado where uf = '" + uf + "'"; //suscetível a SQL Injection
            final var sql = "select * from estado where uf = ?";
            final var statement = conn.prepareStatement(sql);
            //System.out.println(sql);
            statement.setString(1, uf);
            final var result = statement.executeQuery();
            if(result.next()){
                System.out.printf("Id: %2d Nome: %-30s UF: %s\n", result.getInt("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch(SQLException e){
            System.err.println("Erro ao executar consulta SQL: " + e.getMessage());
        }

    }

    private void listarEstados(final Connection conn) {
        try{
            final var statement = conn.createStatement();
            final var result = statement.executeQuery("select * from estado");
            while(result.next()){
                System.out.printf(
                        "Id: %2d Nome: %-30s UF: %s\n",
                        result.getInt("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
    }

    /**
     * Carrega o driver JDBC para o banco de dados a ser utilizado.
     * @deprecated Não é mais necessário nas versões atuais do JDBC.
     */
    @Deprecated
    private void carregarDriverJDBC() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Não foi possível carregar a biblioteca para acesso ao banco de dados: " + e.getMessage());
        }
    }
}
