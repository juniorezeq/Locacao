package br.com.klund.locacao.modelo.negocio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tb_fornecedor")
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fornecedor")
	private Long id;
	@Column(name = "nome", length = 80, nullable = false)
	private String nome;
	@Column(name = "fantasia", length = 50, nullable = false)
	private String fantasia;
	@Column(name = "situacao", length = 20)
	private String situacao;
	@Column(name = "uf", length = 20, nullable = false)
	private String uf;
	@Column(name = "telefone", length = 80)
	private String telefone;
	@Column(name = "logradouro", length = 80, nullable = false)
	private String logradouro;
	@Column(name = "numero", length = 15, nullable = false)
	private String numero;
	@Column(name = "bairro", length = 50, nullable = false)
	private String bairro;
	@Column(name = "cep", length = 15)
	private String cep;
	@Column(name = "municipio", length = 50, nullable = false)
	private String municipio;
	@Column(name = "cnpj", length = 20, nullable = false)
	private String cnpj;
	@OneToMany(mappedBy="fornecedor", cascade = CascadeType.ALL)
	 private List<Equipamento> equipamentos;

	
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "nome=" + nome + ", cnpj=" + cnpj;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}
	
	

}