package exemplojdbc;

/**
 * Classe base fornecendo configurações para os exemmplos.
 * @author Manoel Campos
 */
abstract class Base {
    /**
     * String de conexão com o banco de dados.
     * O banco de dados utilizado é o Apache H2, que está configurado
     * na string de conexão para ser um banco em memória.
     * Você pode criar o banco em arquivo, alterando a estrutura da String de conexão para
     * jdbc:h2:file:/caminho/nome_do_banco
     * Por exemplo: jdbc:h2:file:./banco_exemplo
     * onde ./ indica o diretório atual do projeto.
     */
    protected static final String CONNECTION_URL = "jdbc:h2:file:./banco_exemplo";
    protected static final String USERNAME = "sa";
    protected static final String PASSWORD = "password";
}
