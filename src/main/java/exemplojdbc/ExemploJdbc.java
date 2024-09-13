package exemplojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Manoel Campos
 */
public class ExemploJdbc {
    private final String CONNECTION_URL = "jdbc:h2:mem:meubanco";
    private final String USERNAME = "sa";
    private final String PASSWORD = "password";

    public static void main(String[] args) {
        new ExemploJdbc();
    }

    public ExemploJdbc() {
        try(final var conn = getConnection()){
            System.out.printf("Conexão com o banco realizada com sucesso: %s%n%n", CONNECTION_URL);

            SQLUtils.runFile(conn, "schema.sql");
            listarEstados(conn);
            localizarEstado(conn, "PR");
            listarDadosTabela(conn, "produto");
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }
    }

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
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Não foi possível carregar a biblioteca para acesso ao banco de dados: " + e.getMessage());
        }
    }
}
