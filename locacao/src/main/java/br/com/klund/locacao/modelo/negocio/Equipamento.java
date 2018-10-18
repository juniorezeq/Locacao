package br.com.klund.locacao.modelo.negocio;

import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "tb_equipamento")
public class Equipamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "Modelo", length = 90, nullable = false)
	private String modelo;
	@Column(name = "descricao", length = 90, nullable = false)
	private String descricao;
	@Column(name = "swl", length = 20, nullable = false)
	private String swl;
	@Column(name = "tag", length = 20, nullable = false, unique= true)
	private String tag;
	@Column(name = "valorNota")
	private Float valorNota;
	@Column(name = "precoDiariaMensal")
	private Float precoDiariaMensal;
	@Column(name = "precoDiariaQuinzenal")
	private Float precoDiariaQuinzenal;
	@Column (name = "status")
	private StatusEquipamento status;
	@Column (name = "dataCertificacao")
	private LocalDate dataCertificacao;
	@Column (name = "validadeCertificacao")
	private LocalDate validadeCertificacao;
	@Column (name = "pastacertificados")
	private String pastaCertificados;
	@Column(name = "elevacao", length = 20)
	private String elevacao;
	@ManyToMany
	@JoinColumn(name = "id_equipamento", referencedColumnName = "id_equipamento")
	private List<Locacao> locacoes;
	@ManyToOne
	@JoinColumn(name = "id_fornecedor", referencedColumnName = "id_fornecedor")
	private Fornecedor fornecedor;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public List<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equipamento other = (Equipamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSwl() {
		return swl;
	}
	
	

	public String getPastaCertificados() {
		return pastaCertificados;
	}

	public void setPastaCertificados(String pastaCertificados) {
		this.pastaCertificados = pastaCertificados;
	}

	public void setSwl(String swl) {
		this.swl = swl;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Float getValorNota() {
		return valorNota;
	}

	public void setValorNota(Float valorNota) {
		this.valorNota = valorNota;
	}

	public Float getPrecoDiariaMensal() {
		return precoDiariaMensal;
	}

	public void setPrecoDiariaMensal(Float precoDiariaMensal) {
		this.precoDiariaMensal = precoDiariaMensal;
	}

	public Float getPrecoDiariaQuinzenal() {
		return precoDiariaQuinzenal;
	}

	public void setPrecoDiariaQuinzenal(Float precoDiariaQuinzenal) {
		this.precoDiariaQuinzenal = precoDiariaQuinzenal;
	}

	public StatusEquipamento getStatus() {
		return status;
	}

	public void setStatus(StatusEquipamento status) {
		this.status = status;
	}



	public LocalDate getDataCertificacao() {
		return dataCertificacao;
	}

	public void setDataCertificacao(LocalDate dataCertificacao) {
		this.dataCertificacao = dataCertificacao;
	}

	public LocalDate getValidadeCertificacao() {
		return validadeCertificacao;
	}

	public void setValidadeCertificacao(LocalDate validadeCertificacao) {
		this.validadeCertificacao = validadeCertificacao;
	}

	public String getElevacao() {
		return elevacao;
	}

	public void setElevacao(String elevacao) {
		this.elevacao = elevacao;
	}

	public String valorRealDiariaMensal() {
		NumberFormat formatoReal = NumberFormat.getCurrencyInstance();
		return formatoReal.format(this.precoDiariaMensal);
	}
	
	public String valorRealDiariaQuinzenal() {
		NumberFormat formatoReal = NumberFormat.getCurrencyInstance();
		return formatoReal.format(this.precoDiariaQuinzenal);
	}
	
	
	public String valorRealValorNF() {
		NumberFormat formatoReal = NumberFormat.getCurrencyInstance();
		return formatoReal.format(this.valorNota);
	}
	
	public String validadeCertificacaoFormatada() {
		 return this.validadeCertificacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	public String dataCertificacaoFormatada() {
		       return this.dataCertificacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public String toString() {
		return "Tag: " + tag + ", descricao: " + descricao + ", swl: " + swl;
	}

	
}
