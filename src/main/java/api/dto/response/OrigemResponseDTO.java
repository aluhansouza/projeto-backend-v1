package api.dto.response;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

@Relation(collectionRelation = "origens")
public class OrigemResponseDTO extends RepresentationModel<OrigemResponseDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public OrigemResponseDTO() {}

    public OrigemResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrigemResponseDTO)) return false;
        OrigemResponseDTO that = (OrigemResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "OrigemResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
