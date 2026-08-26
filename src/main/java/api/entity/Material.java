package api.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Table(name = "tb_material")
@Entity
public class Material  implements Serializable {


    private static final long serialVersionUID = 1L;

    public enum Situacao {
        DISPONÍVEL,
        EMPRESTADO,
        DANIFICADO,
        DESATIVADO,
        MANUTENÇÃO
    }

    public enum TipoDepreciacao {
        LINEAR,
        ACELERADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, length = 38)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false)
    private Material.Situacao situacao = Situacao.DISPONÍVEL;

    @Column(name = "patrimonio", unique = true, length = 12)
    private String patrimonio;

    @Column(name = "numserie", length = 40)
    private String numserie;

    @Column(name = "modelo", length = 40)
    private String modelo;

    @Column(name = "imagem_url")
    private String imagemUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id")
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origem_id")
    private Origem origem;

    @Column(name = "localizacao_fisica", length = 50)
    private String localizacaoFisica;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "valor_compra", precision = 10, scale = 2)
    private BigDecimal valorCompra;

    @Column(name = "identificacao_recibo", length = 30)
    private String identificacaoRecibo;

    @Column(name = "qr_valor", length = 255)
    private String qrValor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_depreciacao", nullable = false)
    private Material.TipoDepreciacao tipoDepreciacao = TipoDepreciacao.LINEAR;

    @Column(name = "percentual_depreciacao", precision = 5, scale = 2)
    private BigDecimal percentualDepreciacao;

    @Column(name = "vida_util_anos")
    private Integer vidaUtilAnos;

    @Column(name = "valor_atual", precision = 10, scale = 2)
    private BigDecimal valorAtual;

    // Construtores
    public Material() {}

    public Material(Long id, String nome, Situacao situacao, String patrimonio, String numserie, String modelo, String imagemUrl, Categoria categoria, Setor setor, Marca marca, Origem origem, String localizacaoFisica, LocalDate dataAquisicao, String descricao, BigDecimal valorCompra, String identificacaoRecibo, String qrValor, TipoDepreciacao tipoDepreciacao, BigDecimal percentualDepreciacao, Integer vidaUtilAnos, BigDecimal valorAtual) {
        this.id = id;
        this.nome = nome;
        this.situacao = situacao;
        this.patrimonio = patrimonio;
        this.numserie = numserie;
        this.modelo = modelo;
        this.imagemUrl = imagemUrl;
        this.categoria = categoria;
        this.setor = setor;
        this.marca = marca;
        this.origem = origem;
        this.localizacaoFisica = localizacaoFisica;
        this.dataAquisicao = dataAquisicao;
        this.descricao = descricao;
        this.valorCompra = valorCompra;
        this.identificacaoRecibo = identificacaoRecibo;
        this.qrValor = qrValor;
        this.tipoDepreciacao = tipoDepreciacao;
        this.percentualDepreciacao = percentualDepreciacao;
        this.vidaUtilAnos = vidaUtilAnos;
        this.valorAtual = valorAtual;
    }

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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Objects.equals(id, material.id) && Objects.equals(nome, material.nome) && situacao == material.situacao && Objects.equals(patrimonio, material.patrimonio) && Objects.equals(numserie, material.numserie) && Objects.equals(modelo, material.modelo) && Objects.equals(imagemUrl, material.imagemUrl) && Objects.equals(categoria, material.categoria) && Objects.equals(setor, material.setor) && Objects.equals(marca, material.marca) && Objects.equals(origem, material.origem) && Objects.equals(localizacaoFisica, material.localizacaoFisica) && Objects.equals(dataAquisicao, material.dataAquisicao) && Objects.equals(descricao, material.descricao) && Objects.equals(valorCompra, material.valorCompra) && Objects.equals(identificacaoRecibo, material.identificacaoRecibo) && Objects.equals(qrValor, material.qrValor) && tipoDepreciacao == material.tipoDepreciacao && Objects.equals(percentualDepreciacao, material.percentualDepreciacao) && Objects.equals(vidaUtilAnos, material.vidaUtilAnos) && Objects.equals(valorAtual, material.valorAtual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, situacao, patrimonio, numserie, modelo, imagemUrl, categoria, setor, marca, origem, localizacaoFisica, dataAquisicao, descricao, valorCompra, identificacaoRecibo, qrValor, tipoDepreciacao, percentualDepreciacao, vidaUtilAnos, valorAtual);
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", situacao=" + situacao +
                ", patrimonio='" + patrimonio + '\'' +
                ", numserie='" + numserie + '\'' +
                ", modelo='" + modelo + '\'' +
                ", imagemUrl='" + imagemUrl + '\'' +
                ", categoria=" + categoria +
                ", setor=" + setor +
                ", marca=" + marca +
                ", origem=" + origem +
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
