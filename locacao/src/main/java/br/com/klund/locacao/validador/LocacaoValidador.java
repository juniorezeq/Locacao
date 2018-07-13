package br.com.klund.locacao.validador;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.klund.locacao.modelo.dao.LocacaoDao;
import br.com.klund.locacao.modelo.negocio.Locacao;
import br.com.klund.locacao.modelo.negocio.StatusEquipamento;
import br.com.klund.locacao.modelo.negocio.Equipamento;


@Named
@RequestScoped
public class LocacaoValidador {

	private String mensagem;
	@Inject
	private LocacaoDao locacaoDao;	

	public boolean naoPodeIncluir(Locacao locacao) {
		
		boolean teste = false;
		
		if (locacao.getCodigo().isEmpty()) {
			mensagem = "Código não foi informado";
			teste = true;
			return teste;
		}
		
		if (locacao.getDataFim()==null) {
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
