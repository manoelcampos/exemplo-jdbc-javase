package exemplojdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Um exemplo que mostra como criar e gerenciar transações.
 * @author Manoel Campos
 */
public class JdbcExemplo4Transacoes extends Base {
    public static void main(String[] args) throws SQLException {
        new JdbcExemplo4Transacoes();
    }

    public JdbcExemplo4Transacoes() throws SQLException {
        final Connection conn;
        try {
            conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return;
        }

        final int estadoId = 27; // Tocantins
        /*
        Usa "try with resources" do JDK 9 (originalmente introduzido no JDK 7)
        para fechar a conexão automaticamente. */
        try{
            SQLUtils.runFile(conn, "schema.sql"); // Cria as tabelas e popula o banco
            inserirCidades(conn, estadoId);
            listarCidades(conn, estadoId);
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
            listarCidades(conn, estadoId);
        } finally {
            conn.close();
        }
    }

    private static void inserirCidades(Connection conn, int estadoId) throws SQLException {
        final String[] cidades = {"Palmas", null};
        String sql = "insert into cidade (nome, estado_id) values (?, ?)";
        final PreparedStatement statement = conn.prepareStatement(sql);
        for (String cidade : cidades) {
            statement.setString(1, cidade);
            statement.setLong(2, estadoId);
            statement.executeUpdate();
        }
    }

    private static void listarCidades(final Connection conn, final long estadoId) {
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
