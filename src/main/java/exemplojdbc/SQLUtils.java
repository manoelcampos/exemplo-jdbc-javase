package exemplojdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Manoel Campos
 */
public final class SQLUtils {
    /**
     * Construtor privado para impedir a instanciação da classe.
     */
    private SQLUtils() {/**/}

    public static void runFile(final Connection conn, final String sqlFileName) {
        try (final BufferedReader reader = getFileResource(sqlFileName)) {
            String line;
            var builder = new StringBuilder();
            while((line = getLine(reader)) != null) {
                if(line.isEmpty() || isComment(line)) {
                    continue;
                }

                builder.append(line);
                if(line.endsWith(";")) {
                    //System.out.printf("%s%n%n", builder);
                    executeQuery(conn, builder.toString());
                    builder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static BufferedReader getFileResource(final String fileName) {
        final var inputStream = SQLUtils.class.getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null)
            throw new UncheckedIOException(new IOException("Não foi possível abrir o arquivo " + fileName));

        return new BufferedReader(new InputStreamReader(inputStream));
    }

    private static String getLine(final BufferedReader reader) throws IOException {
        final  String line = reader.readLine();
        return line == null ? null : line.trim();
    }

    private static boolean isComment(final String line) {
        return line.startsWith("--") || line.startsWith("//") || line.startsWith("#");
    }

    private static void executeQuery(final Connection conn, final String sql) {
        try {
            conn.createStatement().execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
