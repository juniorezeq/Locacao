package br.com.klund.locacao.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.klund.locacao.bean.ws.ApiCNPJ;
import br.com.klund.locacao.modelo.dao.FornecedorDao;
import br.com.klund.locacao.modelo.negocio.Fornecedor;
import br.com.klund.locacao.modelo.negocio.Usuario;
import br.com.klund.locacao.modelo.webservice.CNPJ_RWS;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class FornecedorBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO_LOGADO = "usuarioLogado";

	@Inject
	private HttpSession session;
	@Inject
	private FornecedorDao fornecedorDao;
	@Inject
	private Fornecedor fornecedor = new Fornecedor();
	@Inject
	private ApiCNPJ apiCnpj;
	@Inject
	private Usuario usuario;


	@PostConstruct
	public void init() {
		System.out.println("FornecedorBean.init();");
		usuario = (Usuario) session.getAttribute(USUARIO_LOGADO);
		if (usuario == null) {
			usuario = new Usuario();
		}
		fornecedor = new Fornecedor();
		apiCnpj = new ApiCNPJ();
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastro/cadastrofornecedor.xhtml?faces-redirect=true";
	}

	@Transacional
	public String listaFornecedor() {
		return "/view/cadastro/listarfornecedor.xhtml?faces-redirect=true";
	}

	
	@Transacional
	public String iniciaalteracaoFornecedor() {
		return "/view/alteracao/alteracaofornecedor.xhtml?faces-redirect=true";
	}

	@Transacional
	public String limpar() {
		fornecedor = new Fornecedor();
		return null;
	}

	@Transacional
	public String checarCnpj() {
		Fornecedor fornecedorbuscaDao = fornecedorDao.buscaCnpj(fornecedor.getCnpj());
		if (fornecedorbuscaDao == null) {
			mensagemErro("Fornecedor não foi encontrado verifique o CNPJ digitado");			
            return null;
            }
		System.out.println(fornecedorbuscaDao.getCnpj());
		fornecedor = fornecedorbuscaDao;
		return null;
	}

	@Transacional
	public void buscarReceita() {
		CNPJ_RWS recebido = apiCnpj.test(fornecedor.getCnpj());
		if (recebido.getCnpj() != null) {
			System.out.print(recebido.getNome());
			fornecedor.setCnpj(recebido.getCnpj());
			fornecedor.setNome(recebido.getNome());
			fornecedor.setLogradouro(recebido.getLogradouro());
			fornecedor.setNumero(recebido.getNumero());
			fornecedor.setBairro(recebido.getBairro());
			fornecedor.setMunicipio(recebido.getMunicipio());
			fornecedor.setUf(recebido.getUf());
			fornecedor.setTelefone(recebido.getTelefone());
			fornecedor.setSituacao(recebido.getSituacao());
			return;
		}
		limpar();
		mensagemErro(" verifique o CNPJ digitado");
	}

	@Transacional
	public String incluir() {
		boolean existe = fornecedorDao.existeCnpj(fornecedor);
		if (existe == false) {
			fornecedorDao.adiciona(fornecedor);
			mensagemSucesso("Cadastrado com sucesso");
			fornecedor = new Fornecedor();
			return null;
		}
		mensagemErro("O CNPJ informado pertence a outra empresa cadastrada");
		fornecedor = new Fornecedor();
		return null;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Transacional
	public String atualizaFornecedor() {
		fornecedorDao.atualiza(fornecedor);
		System.out.println("encontrado" + fornecedor.getNome());
		mensagemSucesso("Atualizada Corretamente!");
		fornecedor = new Fornecedor();
		return null;
	}
	@Transacional
	public String excluirFornecedor() {
		try {
			fornecedorDao.remove(fornecedor);
			mensagemSucesso ("Excluido corretamente!");
		} catch (Exception e) {
			mensagemErro("Não foi possivel Excluir");
		}
		limpar();
		return null;
	}

	@Transacional
	public List<Fornecedor> fornecedorsCadastrados() {
		List<Fornecedor> fornecedor = new ArrayList<Fornecedor>();
		fornecedor = fornecedorDao.listaTodosPaginada(0, 100);
		return fornecedor;
	}

	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}

}