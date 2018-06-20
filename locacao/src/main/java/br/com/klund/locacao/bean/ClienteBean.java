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
import br.com.klund.locacao.modelo.dao.ClienteDao;
import br.com.klund.locacao.modelo.negocio.Cliente;
import br.com.klund.locacao.modelo.negocio.Usuario;
import br.com.klund.locacao.modelo.webservice.CNPJ_RWS;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ClienteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO_LOGADO = "usuarioLogado";

	@Inject
	private HttpSession session;
	@Inject
	private ClienteDao clienteDao;
	@Inject
	private Cliente cliente = new Cliente();
	@Inject
	private ApiCNPJ apiCnpj;
	@Inject
	private Usuario usuario;


	@PostConstruct
	public void init() {
		System.out.println("ClienteBean.init();");
		usuario = (Usuario) session.getAttribute(USUARIO_LOGADO);
		if (usuario == null) {
			usuario = new Usuario();
		}
		cliente = new Cliente();
		apiCnpj = new ApiCNPJ();
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastro/cadastrocliente.xhtml?faces-redirect=true";
	}

	@Transacional
	public String listaCliente() {
		return "/view/cadastro/listacliente.xhtml?faces-redirect=true";
	}

	
	@Transacional
	public String iniciaalteracaoCliente() {
		return "/view/alteracao/alteracaocliente.xhtml?faces-redirect=true";
	}

	@Transacional
	public String limpar() {
		cliente = new Cliente();
		return null;
	}

	@Transacional
	public String checarCnpj() {
		Cliente clientebuscaDao = clienteDao.buscaCnpj(cliente.getCnpj());
		if (clientebuscaDao == null) {
			mensagemErro("Cliente não foi encontrado verifique o CNPJ digitado");			
            return null;
            }
		System.out.println(clientebuscaDao.getCnpj());
		cliente = clientebuscaDao;
		return null;
	}

	@Transacional
	public void buscarReceita() {
		CNPJ_RWS recebido = apiCnpj.test(cliente.getCnpj());
		if (recebido.getCnpj() != null) {
			System.out.print(recebido.getNome());
			cliente.setCnpj(recebido.getCnpj());
			cliente.setNome(recebido.getNome());
			cliente.setLogradouro(recebido.getLogradouro());
			cliente.setNumero(recebido.getNumero());
			cliente.setBairro(recebido.getBairro());
			cliente.setMunicipio(recebido.getMunicipio());
			cliente.setUf(recebido.getUf());
			cliente.setTelefone(recebido.getTelefone());
			cliente.setSituacao(recebido.getSituacao());
			return;
		}
		limpar();
		mensagemErro(" verifique o CNPJ digitado");
	}

	@Transacional
	public String incluir() {
		boolean existe = clienteDao.existeCnpj(cliente);
		if (existe == false) {
			clienteDao.adiciona(cliente);
			mensagemSucesso("Cadastrado com sucesso");
			cliente = new Cliente();
			return null;
		}
		mensagemErro("O CNPJ informado pertence a outra empresa cadastrada");
		cliente = new Cliente();
		return null;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Transacional
	public String atualizaCliente() {
		clienteDao.atualiza(cliente);
		System.out.println("encontrado" + cliente.getNome());
		mensagemSucesso("Atualizada Corretamente!");
		cliente = new Cliente();
		return null;
	}
	@Transacional
	public String excluirCliente() {
		try {
			clienteDao.remove(cliente);
			mensagemSucesso ("Excluido corretamente!");
		} catch (Exception e) {
			mensagemErro("Não foi possivel Excluir");
		}
		limpar();
		return null;
	}

	@Transacional
	public List<Cliente> clientessCadastrados() {
		List<Cliente> clientes = new ArrayList<Cliente>();
		clientes = clienteDao.listaTodosPaginada(0, 100);
		return clientes;
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