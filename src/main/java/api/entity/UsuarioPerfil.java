package api.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tb_usuario_perfil")
public class UsuarioPerfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @Column(name = "data_associacao", nullable = false)
    private LocalDate dataAssociacao;

    @Column(name = "data_remocao")
    private LocalDate dataRemocao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public LocalDate getDataAssociacao() { return dataAssociacao; }
    public void setDataAssociacao(LocalDate dataAssociacao) { this.dataAssociacao = dataAssociacao; }

    public LocalDate getDataRemocao() { return dataRemocao; }
    public void setDataRemocao(LocalDate dataRemocao) { this.dataRemocao = dataRemocao; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioPerfil that = (UsuarioPerfil) o;
        return Objects.equals(id, that.id) && Objects.equals(usuario, that.usuario) && Objects.equals(perfil, that.perfil) && Objects.equals(dataAssociacao, that.dataAssociacao) && Objects.equals(dataRemocao, that.dataRemocao) && Objects.equals(ativo, that.ativo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, perfil, dataAssociacao, dataRemocao, ativo);
    }

    @Override
    public String toString() {
        return "UsuarioPerfil{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", perfil=" + perfil +
                ", dataAssociacao=" + dataAssociacao +
                ", dataRemocao=" + dataRemocao +
                ", ativo=" + ativo +
                '}';
    }
}