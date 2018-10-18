package br.com.klund.locacao.validador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;




@Named
@RequestScoped
public class AlterarSenhaValidador {

	private String mensagem;

	public boolean naoPodeIncluir(String senhaNova1,String senhaNova2,String senhaAtual,String senhaAtualBD) {
		
		boolean teste = false;
		if (senhaAtual!=(senhaAtualBD)) {
			mensagem = "A senha antiga não é correspondente ao cadastro, digite novamente" + " - " + "senhaAtual" + " - " + "senhaAtualBD";
		    teste = true;
			return teste;
		}
		
		if (senhaNova1!=senhaNova2) {
			mensagem = "a confirmação de senha não é compativel, verifique a digitação";
			teste = true;
			return teste;
		}
		
	
		return teste;
	}

	public String getMensagem() {
		return mensagem;
	}


	
}
