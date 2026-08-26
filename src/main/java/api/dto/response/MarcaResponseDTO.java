package api.dto.response;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Objects;

@Relation(collectionRelation = "marcas")
public class MarcaResponseDTO extends RepresentationModel<MarcaResponseDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public MarcaResponseDTO() {}

    public MarcaResponseDTO(Long id, String nome) {
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
        if (!(o instanceof MarcaResponseDTO)) return false;
        MarcaResponseDTO that = (MarcaResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

    @Override
    public String toString() {
        return "MarcaResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
