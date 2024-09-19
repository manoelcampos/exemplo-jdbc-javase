package exemplojdbc;

/**
 * @author Manoel Campos
 */
public class Cidade {
    private Long id;
    private String nome;
    private Estado estado;

    public Cidade(Long id, String nome, Estado estado) {
        this.id = id;
        this.nome = nome;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Estado getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return String.format("Cidade{id: %d, nome: %s, %s}", id, nome, estado);
    }
}
