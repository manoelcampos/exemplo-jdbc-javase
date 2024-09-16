package exemplojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Um exemplo mostrando como usar {@link java.sql.PreparedStatement}s para passar
 * parâmetros para um comando SQL e realizar operações de insert e delete.
 *
 * <p>Para um código melhor estruturado, veja {@link JdbcExemplo3}.</p>
 * @author Manoel Campos
 */
public class JdbcExemplo2 extends ExemploBase {
    private static final long estadoId = 27;
    public static void main(String[] args) {
        new JdbcExemplo2();
    }

    public JdbcExemplo2() {
        final Connection conn;
        try {
            conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return;
        }

        try(conn){
            SQLUtils.runFile(conn, "schema.sql"); // Cria as tabelas e popula o banco

            inserirCidades(conn);
            listarCidades(conn);
            excluirCidade(conn);
            listarCidades(conn);

            System.out.println();
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }

    private static void excluirCidade(final Connection conn) throws SQLException {
        final int cidadeId = 13;

        final PreparedStatement statement = conn.prepareStatement("delete from cidade where id = ?");
        statement.setLong(1, cidadeId);
        statement.executeUpdate();

        System.out.printf("Excluída cidade: %d%n%n", cidadeId);
    }

    private static void inserirCidades(Connection conn) throws SQLException {
        final String[] cidades = {"Palmas", "Araguaína", "Cidade Teste"};
        final var statement = conn.prepareStatement("insert into cidade (nome, estado_id) values (?, ?)");
        for (String cidade : cidades) {
            statement.setString(1, cidade);
            statement.setLong(2, estadoId);
            statement.executeUpdate();
        }
    }

    private static void listarCidades(final Connection conn) {
        try{
            final var statement = conn.prepareStatement("select * from cidade where estado_id = ?");
            statement.setLong(1, estadoId);
            final var result = statement.executeQuery();
            System.out.println("Cidades do Estado " + estadoId);
            while(result.next()){
                System.out.printf(
                        "Id: %2d Cidade: %s\n",
                        result.getInt("id"), result.getString("nome"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }
}
