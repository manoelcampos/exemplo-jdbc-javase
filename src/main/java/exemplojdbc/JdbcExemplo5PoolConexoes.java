package exemplojdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Um exemplo mostrando como um pool de conexões que representa um
 * repositório contendo uma lista de conexões permanentes com o banco,
 * que são abertas e não são fechadas, sendo apenas reaproveitadas
 * cada vez que uma conexão é solicitada.
 *
 * <p>Como a criação de conexões com o banco é um processo computacionalmente
 * custoso e que pode demorar (principalmente se o servidor for remoto),
 * abrir e fechar conexões não é algo eficiente em aplicações web que tenham
 * múltiplos usuários simultâneos.
 * Assim, um pool de conexões inicia já abrindo uma número configurado
 * de conexões e nunca as fecha.
 * Quando a aplicação pede pra abrir uma conexão, ela irá
 * apenas obter uma já aberta do pool.
 * Quando a aplicação chamar o método {@link Connection#close()}
 * para fechar uma conexão, tal conexão é apenas devolvida para o pool
 * para que outros usuários possam usá-la.</p>
 *
 * <p>Como esta não é uma aplicação web e não é usada por múltiplos usuários,
 * não faz sentido usar um pool de conexões.
 * Aplicações web usando frameworks como Spring já gerenciam um pool
 * e você não tem que lidar com ele diretamente.
 * Então, o código aqui é apenas de exemplo de como criar um pool.</p>
 *
 * <p>Para um código melhor estruturado, veja {@link JdbcExemplo3LocalizarListarTabelaGenerica}.</p>
 * @author Manoel Campos
 */
public class JdbcExemplo5PoolConexoes extends Base {
    private static final DataSource dataSource = createDataSource();

    private static DataSource createDataSource(){
        var config = new HikariConfig();
        config.setJdbcUrl(CONNECTION_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setMaximumPoolSize(5);
        return new HikariDataSource(config);
    }

    public static void main(String[] args) {
        new JdbcExemplo5PoolConexoes();
    }

    public JdbcExemplo5PoolConexoes() {
        final long estadoId = 27;
        try{
            runSqlScript();

            final List<Estado> estados = obterEstados();
            imprimirLista(estados, "Estados");

            final int cidadesExcluidas = excluirCidades();
            System.out.printf("Cidades excluídas: %d%n", cidadesExcluidas);

            inserirCidades(estadoId);
            final List<Cidade> cidades = obterCidades(estadoId);
            imprimirLista(cidades, "Cidades do Estado " + estadoId);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }

    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível conectar ao banco de dados", e);
        }
    }

    private List<Estado> obterEstados() throws SQLException {
        try(Connection conn = getConnection()) {
            final var listaEstados = new ArrayList<Estado>();
            final var statement = conn.createStatement();
            final String sql = "select * from estado";
            final var result = statement.executeQuery(sql);
            while(result.next()){
                var estado = new Estado(result.getLong("id"), result.getString("nome"), result.getString("uf"));
                listaEstados.add(estado);
            }

            return listaEstados;
        }
    }

    private void imprimirLista(List<?> lista, String titulo) {
        System.out.printf("%nLista de %s%n", titulo);
        for (var objeto : lista) {
            System.out.println("  " + objeto);
        }
        System.out.println();
    }

    private void runSqlScript() throws SQLException {
        try(Connection conn = getConnection()) {
            SQLUtils.runFile(conn, "schema.sql"); // Cria as tabelas e popula o banco
        }
    }

    private int excluirCidades() throws SQLException {
        System.out.println("Excluindo todas as cidades cadastradas");
        try(Connection conn = getConnection()) {
            String sql = "delete from cidade";
            final Statement statement = conn.createStatement();
            return statement.executeUpdate(sql);
        }
    }

    private void inserirCidades(long estadoId) throws SQLException {
        final String[] cidades = {"Palmas", "Araguaína", "Cidade Teste"};

        try(Connection conn = getConnection()) {
            String sql = "insert into cidade (nome, estado_id) values (?, ?)";
            final var statement = conn.prepareStatement(sql);
            for (String cidade : cidades) {
                statement.setString(1, cidade);
                statement.setLong(2, estadoId);
                statement.executeUpdate();
            }
        }
    }

    private Estado localizarEstado(long estadoId) throws SQLException {
        try(Connection conn = getConnection()) {
            final String sql = "select * from estado where id = ?";
            final var statement = conn.prepareStatement(sql);
            statement.setLong(1, estadoId);
            final var result = statement.executeQuery();
            if(result.next()){
                return new Estado(result.getLong("id"), result.getString("nome"), result.getString("uf"));
            }

            return new Estado();
        }
    }

    private List<Cidade> obterCidades(long estadoId) throws SQLException {
        try(Connection conn = getConnection()) {
            final var listaCidades = new ArrayList<Cidade>();
            final String sql = "select * from cidade where estado_id = ?";
            final var statement = conn.prepareStatement(sql);
            statement.setLong(1, estadoId);
            final var result = statement.executeQuery();
            var estado = localizarEstado(estadoId);
            while(result.next()){
                listaCidades.add(new Cidade(result.getLong("id"), result.getString("nome"), estado));
            }

            return listaCidades;
        }
    }
}
