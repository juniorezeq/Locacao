package br.com.klund.locacao.validador;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.klund.locacao.modelo.negocio.Locacao;
import br.com.klund.locacao.modelo.negocio.StatusEquipamento;
import br.com.klund.locacao.modelo.negocio.Equipamento;


@Named
@RequestScoped
public class LocacaoValidador {

	private String mensagem;

	public boolean naoPodeIncluir(Locacao locacao) {
		
		boolean teste = false;
		if (locacao.getCodigo().isEmpty()) {
			mensagem = "Código não foi informado";
			teste = true;
			return teste;
		}
		
		if (locacao.getEquipamentos().isEmpty()) {
			mensagem = "favor informar os equipamentos";
			teste = true;
			return teste;
		}
		
		if (locacao.getDataInicio()==null) {
			mensagem = "a data inicial não foi informada";
			teste = true;
			return teste;
		}
		
		if (locacao.getCliente()==null) {
			mensagem = "o Cliente deve ser informado";
			teste = true;
			return teste;
		}
		
		if (locacao.getTipoLocacao()==null) {
			mensagem = "o Tipo Locação deve ser informado";
			teste = true;
			return teste;
		}
		
		LocalDate a = LocalDate.now();
		if(locacao.getDataInicio().isAfter(a)) {
			mensagem = "a data Inicio informada é inválida";
			teste = true;
			return teste;
		}
				
		return teste;
	}

	public String getMensagem() {
		return mensagem;
	}

	public boolean naopodeAddEquipamento(Equipamento equipamento, List<Equipamento> lista) {
		boolean resultado = false;
		
		if (equipamento.getStatus()==StatusEquipamento.Alugado) {
			mensagem = "equipamento informado já esta alugado verifique o Tag";
			resultado = true;
			return resultado;
		}
		
		
		if (equipamento.equals(null)) {
			mensagem = "selecione um equipamento válido";
			resultado = true;
			return resultado;
		}
		for(int i=0; i<lista.size();i++) {
			if (lista.get(i).getTag()==equipamento.getTag()) {
				resultado = true;
				mensagem = "equipamento informado já esta na lista";
				return resultado;
			}
		}
		
		
		return resultado;
	}
	
	
	
}
