package exemplojdbc;

/**
 * @author Manoel Campos
 */
public class Estado {
    private Long id = 0L;
    private String nome = "";
    private String uf = "";

    public Estado() {
    }

    public Estado(Long id) {
        this.id = id;
    }

    public Estado(Long id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUf() {
        return uf;
    }

    @Override
    public String toString() {
        return String.format("Estado{id: %d, nome: %s, uf: %s}", id, nome, uf);
    }
}
