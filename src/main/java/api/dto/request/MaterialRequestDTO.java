package api.dto.request;

import api.entity.Material.Situacao;
import api.entity.Material.TipoDepreciacao;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Relation(collectionRelation = "materiais")
public class MaterialRequestDTO extends RepresentationModel<MaterialRequestDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(min = 1, max = 40, message = "Nome deve ter entre 1 e 40 caracteres")
    private String nome;

    @NotNull(message = "Situação não pode ser nula")
    private Situacao situacao;

    @NotNull(message = "Patrimônio não pode ser nulo")
    @Size(min = 1, max = 12, message = "Patrimônio deve ter entre 1 e 12 caracteres")
    private String patrimonio;

    @NotNull(message = "Número de Série não pode ser nulo")
    @Size(min = 1, max = 40, message = "Número de Série deve ter entre 1 e 40 caracteres")
    private String numserie;

    @NotNull(message = "Modelo não pode ser nulo")
    @Size(min = 1, max = 40, message = "Modelo deve ter entre 1 e 40 caracteres")
    private String modelo;

    @NotNull(message = "ID da categoria não pode ser nulo")
    private Long categoriaId;

    @NotNull(message = "ID do setor não pode ser nulo")
    private Long setorId;

    @NotNull(message = "ID da marca não pode ser nulo")
    private Long marcaId;

    @NotNull(message = "ID da origem não pode ser nulo")
    private Long origemId;

    @Size(max = 50, message = "Localização física deve ter no máximo 50 caracteres")
    private String localizacaoFisica;

    @PastOrPresent(message = "Data de aquisição deve ser passada ou atual")
    private LocalDate dataAquisicao;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "Valor de compra não pode ser nulo")
    @DecimalMin(value = "0.00", message = "Valor de compra deve ser maior ou igual a 0.00")
    private BigDecimal valorCompra;

    @Size(max = 30, message = "Identificação do recibo deve ter no máximo 30 caracteres")
    private String identificacaoRecibo;

    @Size(max = 255, message = "QR valor deve ter no máximo 255 caracteres")
    private String qrValor;

    @NotNull(message = "Tipo de depreciação não pode ser nulo")
    private TipoDepreciacao tipoDepreciacao;

    @NotNull(message = "Percentual de depreciação não pode ser nulo")
    @DecimalMin(value = "0.00", message = "Percentual de depreciação deve ser maior ou igual a 0.00")
    @DecimalMax(value = "100.00", message = "Percentual de depreciação deve ser menor ou igual a 100.00")
    private BigDecimal percentualDepreciacao;

    @NotNull(message = "Vida útil em anos não pode ser nulo")
    @Positive(message = "Vida útil em anos deve ser positivo")
    private Integer vidaUtilAnos;

    @NotNull(message = "Valor atual não pode ser nulo")
    @DecimalMin(value = "0.00", message = "Valor atual deve ser maior ou igual a 0.00")
    private BigDecimal valorAtual;

    // Construtores
    public MaterialRequestDTO() {}




    // Getters e Setters
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

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getNumSerie() {
        return numserie;
    }

    public void setNumSerie(String numserie) {
        this.numserie = numserie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getSetorId() {
        return setorId;
    }

    public void setSetorId(Long setorId) {
        this.setorId = setorId;
    }

    public Long getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Long marcaId) {
        this.marcaId = marcaId;
    }    

    public Long getOrigemId() {
        return origemId;
    }

    public void setOrigemId(Long origemId) {
        this.origemId = origemId;
    }

    public String getLocalizacaoFisica() {
        return localizacaoFisica;
    }

    public void setLocalizacaoFisica(String localizacaoFisica) {
        this.localizacaoFisica = localizacaoFisica;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public String getIdentificacaoRecibo() {
        return identificacaoRecibo;
    }

    public void setIdentificacaoRecibo(String identificacaoRecibo) {
        this.identificacaoRecibo = identificacaoRecibo;
    }

    public String getQrValor() {
        return qrValor;
    }

    public void setQrValor(String qrCodeValor) {
        this.qrValor = qrValor;
    }

    public TipoDepreciacao getTipoDepreciacao() {
        return tipoDepreciacao;
    }

    public void setTipoDepreciacao(TipoDepreciacao tipoDepreciacao) {
        this.tipoDepreciacao = tipoDepreciacao;
    }

    public BigDecimal getPercentualDepreciacao() {
        return percentualDepreciacao;
    }

    public void setPercentualDepreciacao(BigDecimal percentualDepreciacao) {
        this.percentualDepreciacao = percentualDepreciacao;
    }

    public Integer getVidaUtilAnos() {
        return vidaUtilAnos;
    }

    public void setVidaUtilAnos(Integer vidaUtilAnos) {
        this.vidaUtilAnos = vidaUtilAnos;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MaterialRequestDTO that = (MaterialRequestDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && situacao == that.situacao && Objects.equals(patrimonio, that.patrimonio) && Objects.equals(numserie, that.numserie) && Objects.equals(modelo, that.modelo) && Objects.equals(categoriaId, that.categoriaId) && Objects.equals(setorId, that.setorId) && Objects.equals(marcaId, that.marcaId) && Objects.equals(origemId, that.origemId) && Objects.equals(localizacaoFisica, that.localizacaoFisica) && Objects.equals(dataAquisicao, that.dataAquisicao) && Objects.equals(descricao, that.descricao) && Objects.equals(valorCompra, that.valorCompra) && Objects.equals(identificacaoRecibo, that.identificacaoRecibo) && Objects.equals(qrValor, that.qrValor) && tipoDepreciacao == that.tipoDepreciacao && Objects.equals(percentualDepreciacao, that.percentualDepreciacao) && Objects.equals(vidaUtilAnos, that.vidaUtilAnos) && Objects.equals(valorAtual, that.valorAtual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nome, situacao, patrimonio, numserie, modelo, categoriaId, setorId, marcaId, origemId, localizacaoFisica, dataAquisicao, descricao, valorCompra, identificacaoRecibo, qrValor, tipoDepreciacao, percentualDepreciacao, vidaUtilAnos, valorAtual);
    }

    @Override
    public String toString() {
        return "MaterialRequestDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", situacao=" + situacao +
                ", patrimonio='" + patrimonio + '\'' +
                ", numserie='" + numserie + '\'' +
                ", modelo='" + modelo + '\'' +
                ", categoriaId=" + categoriaId +
                ", setorId=" + setorId +
                ", marcaId=" + marcaId +
                ", origemId=" + origemId +
                ", localizacaoFisica='" + localizacaoFisica + '\'' +
                ", dataAquisicao=" + dataAquisicao +
                ", descricao='" + descricao + '\'' +
                ", valorCompra=" + valorCompra +
                ", identificacaoRecibo='" + identificacaoRecibo + '\'' +
                ", qrValor='" + qrValor + '\'' +
                ", tipoDepreciacao=" + tipoDepreciacao +
                ", percentualDepreciacao=" + percentualDepreciacao +
                ", vidaUtilAnos=" + vidaUtilAnos +
                ", valorAtual=" + valorAtual +
                '}';
    }
}