package exemplojdbc;

import java.sql.*;

/**
 * Um exemplo minimalista de conexão a um banco de dados Apache H2 utilizando JDBC,
 * implementando em um único método para dar uma ideia geral de como todo o processo funcionoa.
 * Assim, esta classe irá intencionalmente ter código duplicado em relação aos outros exemplos
 * disponíveis, para permitir ver, em um único arquivo, todo o processo de execução de uma consulta SQL via JDBC.
 *
 * <p>Para um código melhor estruturado, veja {@link JdbcExemplo3LocalizarListarTabelaGenerica}.</p>
 * @author Manoel Campos
 */
public class JdbcExemplo1Simplista extends Base {
    public static void main(String[] args) {
        new JdbcExemplo1Simplista();
    }

    public JdbcExemplo1Simplista() {
        final Connection conn;
        try {
            conn = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
            return;
        }

        /*
        Usa "try with resources" do JDK 9 (originalmente introduzido no JDK 7)
        para fechar a conexão automaticamente. */
        try(conn){
            SQLUtils.runFile(conn, "schema.sql"); // Cria as tabelas e popula o banco
            final Statement statement = conn.createStatement();
            String sql = "select * from estado";
            final ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                System.out.printf(
                        "Id: %2d Nome: %-30s UF: %s\n",
                        result.getLong("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }
}
