package api.dto.response;

import api.entity.Categoria;
import api.entity.Material.Situacao;
import api.entity.Material.TipoDepreciacao;
import api.entity.Origem;
import api.entity.Setor;
import api.entity.Marca;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Relation(collectionRelation = "materiais")
public class MaterialResponseDTO extends RepresentationModel<MaterialResponseDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private Situacao situacao;

    private String patrimonio;

    private String numserie;

    private String modelo;

    private String imagemUrl;

    private Long categoriaId;

    private Long setorId;

    private Long marcaId;

    private Long origemId;

    private String localizacaoFisica;

    private LocalDate dataAquisicao;

    private String descricao;

    private BigDecimal valorCompra;

    private String identificacaoRecibo;

    private String qrValor;

    private TipoDepreciacao tipoDepreciacao;

    private BigDecimal percentualDepreciacao;

    private Integer vidaUtilAnos;

    private BigDecimal valorAtual;

    private Categoria categoria;
    private Setor setor;
    private Marca marca;
    private Origem origem;


    // Construtores
    public MaterialResponseDTO() {}

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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
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

    public void setQrValor(String qrValor) {
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Origem getOrigem() {
        return origem;
    }

    public void setOrigem(Origem origem) {
        this.origem = origem;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MaterialResponseDTO that = (MaterialResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && situacao == that.situacao && Objects.equals(patrimonio, that.patrimonio) && Objects.equals(numserie, that.numserie) && Objects.equals(modelo, that.modelo) && Objects.equals(imagemUrl, that.imagemUrl) && Objects.equals(categoriaId, that.categoriaId) && Objects.equals(setorId, that.setorId) && Objects.equals(marcaId, that.marcaId) && Objects.equals(origemId, that.origemId) && Objects.equals(localizacaoFisica, that.localizacaoFisica) && Objects.equals(dataAquisicao, that.dataAquisicao) && Objects.equals(descricao, that.descricao) && Objects.equals(valorCompra, that.valorCompra) && Objects.equals(identificacaoRecibo, that.identificacaoRecibo) && Objects.equals(qrValor, that.qrValor) && tipoDepreciacao == that.tipoDepreciacao && Objects.equals(percentualDepreciacao, that.percentualDepreciacao) && Objects.equals(vidaUtilAnos, that.vidaUtilAnos) && Objects.equals(valorAtual, that.valorAtual) && Objects.equals(categoria, that.categoria) && Objects.equals(setor, that.setor) && Objects.equals(marca, that.marca) && Objects.equals(origem, that.origem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, nome, situacao, patrimonio,  numserie,  modelo, imagemUrl, categoriaId, setorId, marcaId, origemId, localizacaoFisica, dataAquisicao, descricao, valorCompra, identificacaoRecibo, qrValor, tipoDepreciacao, percentualDepreciacao, vidaUtilAnos, valorAtual, categoria, setor, marca, origem);
    }

    @Override
    public String toString() {
        return "MaterialResponseDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", situacao=" + situacao +
                ", patrimonio='" + patrimonio + '\'' +
                ", numserie='" + numserie + '\'' +
                ", modelo='" + modelo + '\'' +
                ", imagemUrl='" + imagemUrl + '\'' +
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
                ", categoria=" + categoria +
                ", setor=" + setor +
                ", marca=" + marca +
                ", origem=" + origem +
                '}';
    }
}