package br.com.klund.locacao.modelo.webservice;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CNPJ_RWS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("atividade_principal")
	private List<AtividadePrincipal> atividadePrincipal = null;
	@JsonProperty("data_situacao")
	private String dataSituacao;
	@JsonProperty("nome")
	private String nome;
	@JsonProperty("uf")
	private String uf;
	@JsonProperty("telefone")
	private String telefone;
	@JsonProperty("atividades_secundarias")
	private List<AtividadesSecundaria> atividadesSecundarias = null;
	@JsonProperty("qsa")
	private List<Qsa> qsa = null;
	@JsonProperty("situacao")
	private String situacao;
	@JsonProperty("bairro")
	private String bairro;
	@JsonProperty("logradouro")
	private String logradouro;
	@JsonProperty("numero")
	private String numero;
	@JsonProperty("cep")
	private String cep;
	@JsonProperty("municipio")
	private String municipio;
	@JsonProperty("abertura")
	private String abertura;
	@JsonProperty("natureza_juridica")
	private String naturezaJuridica;
	@JsonProperty("fantasia")
	private String fantasia;
	@JsonProperty("cnpj")
	private String cnpj;
	@JsonProperty("ultima_atualizacao")
	private String ultimaAtualizacao;
	@JsonProperty("status")
	private String status;
	@JsonProperty("tipo")
	private String tipo;
	@JsonProperty("complemento")
	private String complemento;
	@JsonProperty("email")
	private String email;
	@JsonProperty("efr")
	private String efr;
	@JsonProperty("motivo_situacao")
	private String motivoSituacao;
	@JsonProperty("situacao_especial")
	private String situacaoEspecial;
	@JsonProperty("data_situacao_especial")
	private String dataSituacaoEspecial;
	@JsonProperty("capital_social")
	private String capitalSocial;
	@JsonProperty("extra")
	private Extra extra;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("atividade_principal")
	public List<AtividadePrincipal> getAtividadePrincipal() {
		return atividadePrincipal;
	}

	@JsonProperty("atividade_principal")
	public void setAtividadePrincipal(List<AtividadePrincipal> atividadePrincipal) {
		this.atividadePrincipal = atividadePrincipal;
	}

	@JsonProperty("data_situacao")
	public String getDataSituacao() {
		return dataSituacao;
	}

	@JsonProperty("data_situacao")
	public void setDataSituacao(String dataSituacao) {
		this.dataSituacao = dataSituacao;
	}

	@JsonProperty("nome")
	public String getNome() {
		return nome;
	}

	@JsonProperty("nome")
	public void setNome(String nome) {
		this.nome = nome;
	}

	@JsonProperty("uf")
	public String getUf() {
		return uf;
	}

	@JsonProperty("uf")
	public void setUf(String uf) {
		this.uf = uf;
	}

	@JsonProperty("telefone")
	public String getTelefone() {
		return telefone;
	}

	@JsonProperty("telefone")
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@JsonProperty("atividades_secundarias")
	public List<AtividadesSecundaria> getAtividadesSecundarias() {
		return atividadesSecundarias;
	}

	@JsonProperty("atividades_secundarias")
	public void setAtividadesSecundarias(List<AtividadesSecundaria> atividadesSecundarias) {
		this.atividadesSecundarias = atividadesSecundarias;
	}

	@JsonProperty("qsa")
	public List<Qsa> getQsa() {
		return qsa;
	}

	@JsonProperty("qsa")
	public void setQsa(List<Qsa> qsa) {
		this.qsa = qsa;
	}

	@JsonProperty("situacao")
	public String getSituacao() {
		return situacao;
	}

	@JsonProperty("situacao")
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@JsonProperty("bairro")
	public String getBairro() {
		return bairro;
	}

	@JsonProperty("bairro")
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@JsonProperty("logradouro")
	public String getLogradouro() {
		return logradouro;
	}

	@JsonProperty("logradouro")
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@JsonProperty("numero")
	public String getNumero() {
		return numero;
	}

	@JsonProperty("numero")
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@JsonProperty("cep")
	public String getCep() {
		return cep;
	}

	@JsonProperty("cep")
	public void setCep(String cep) {
		this.cep = cep;
	}

	@JsonProperty("municipio")
	public String getMunicipio() {
		return municipio;
	}

	@JsonProperty("municipio")
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	@JsonProperty("abertura")
	public String getAbertura() {
		return abertura;
	}

	@JsonProperty("abertura")
	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	@JsonProperty("natureza_juridica")
	public String getNaturezaJuridica() {
		return naturezaJuridica;
	}

	@JsonProperty("natureza_juridica")
	public void setNaturezaJuridica(String naturezaJuridica) {
		this.naturezaJuridica = naturezaJuridica;
	}

	@JsonProperty("fantasia")
	public String getFantasia() {
		return fantasia;
	}

	@JsonProperty("fantasia")
	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	@JsonProperty("cnpj")
	public String getCnpj() {
		return cnpj;
	}

	@JsonProperty("cnpj")
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@JsonProperty("ultima_atualizacao")
	public String getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	@JsonProperty("ultima_atualizacao")
	public void setUltimaAtualizacao(String ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("tipo")
	public String getTipo() {
		return tipo;
	}

	@JsonProperty("tipo")
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@JsonProperty("complemento")
	public String getComplemento() {
		return complemento;
	}

	@JsonProperty("complemento")
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("efr")
	public String getEfr() {
		return efr;
	}

	@JsonProperty("efr")
	public void setEfr(String efr) {
		this.efr = efr;
	}

	@JsonProperty("motivo_situacao")
	public String getMotivoSituacao() {
		return motivoSituacao;
	}

	@JsonProperty("motivo_situacao")
	public void setMotivoSituacao(String motivoSituacao) {
		this.motivoSituacao = motivoSituacao;
	}

	@JsonProperty("situacao_especial")
	public String getSituacaoEspecial() {
		return situacaoEspecial;
	}

	@JsonProperty("situacao_especial")
	public void setSituacaoEspecial(String situacaoEspecial) {
		this.situacaoEspecial = situacaoEspecial;
	}

	@JsonProperty("data_situacao_especial")
	public String getDataSituacaoEspecial() {
		return dataSituacaoEspecial;
	}

	@JsonProperty("data_situacao_especial")
	public void setDataSituacaoEspecial(String dataSituacaoEspecial) {
		this.dataSituacaoEspecial = dataSituacaoEspecial;
	}

	@JsonProperty("capital_social")
	public String getCapitalSocial() {
		return capitalSocial;
	}

	@JsonProperty("capital_social")
	public void setCapitalSocial(String capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	@JsonProperty("extra")
	public Extra getExtra() {
		return extra;
	}

	@JsonProperty("extra")
	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return cnpj +" " + bairro+ " " + fantasia + " " +uf +" " + capitalSocial +" " + complemento;
	}
	
	
}
