package br.com.klund.locacao.validador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.negocio.Equipamento;


@Named
@RequestScoped
public class EquipamentoValidador {

	private String mensagem;
	@Inject
	private EquipamentoDao equipamentoDao;	

	public boolean naoPodeIncluir(Equipamento equipamento) {
		
		boolean teste = false;
		
		if (equipamento.getDescricao().isEmpty()) {
			mensagem = "Descrição não foi informado";
			teste = true;
			return teste;
		}
		
		if (equipamento.getModelo().isEmpty()) {
			mensagem = "modelo não foi informado";
			teste = true;
			return teste;
		}
		
		if (equipamento.getSwl().isEmpty()) {
			mensagem = "SWL não foi informado";
			teste = true;
			return teste;
		}
		
		if (equipamento.getTag().isEmpty()) {
			mensagem = "Tag não foi informado";
			teste = true;
			return teste;
		}
		
		if (equipamentoDao.existeTag(equipamento)) {
			mensagem = "Tag já foi cadastrado";
			teste = true;
			return teste;
		}
		
		return teste;
	}

	public String getMensagem() {
		return mensagem;
	}

}
