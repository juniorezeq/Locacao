package br.com.klund.locacao.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.dao.LocacaoDao;
import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.modelo.negocio.Locacao;
import br.com.klund.locacao.modelo.negocio.StatusEquipamento;
import br.com.klund.locacao.modelo.negocio.StatusLocacao;
import br.com.klund.locacao.modelo.negocio.Usuario;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class LocacaoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String USUARIO_LOGADO = "usuarioLogado";

	@Inject
	private HttpSession session;
	@Inject
	private LocacaoDao locacaoDao;
	@Inject
	private Locacao locacao = new Locacao();
	@Inject
	private Usuario usuario;
	private String codBuscar;
	private String tag;
	@Inject
	private Equipamento equipamento = new Equipamento();
	@Inject
	private EquipamentoDao equipamentoDao = new EquipamentoDao();
	private List<Equipamento> listaEquipamentos = new ArrayList<Equipamento> ();



	@PostConstruct
	public void init() {
		System.out.println("LocacaoBean.init();");
		usuario = (Usuario) session.getAttribute(USUARIO_LOGADO);
		if (usuario == null) {
			usuario = new Usuario();
		}
		locacao = new Locacao();
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastro/cadastrolocacao.xhtml?faces-redirect=true";
	}

	@Transacional
	public String listaLocacao() {
		return "/view/cadastro/listarlocacao.xhtml?faces-redirect=true";
	}

	
	@Transacional
	public String alterarLocacao() {
		return "/view/cadastro/editarlocacao.xhtml?faces-redirect=true";
	}

	@Transacional
	public String limpar() {
		locacao = new Locacao();
		return null;
	}

	@Transacional
	public String buscarporCodigo() {
		Locacao locacaobuscaDao = locacaoDao.buscaCodigo(codBuscar);
		if (locacaobuscaDao == null) {
			mensagemErro("Locacao não foi encontrado verifique o código digitado");			
            return null;
            }
		System.out.println(locacaobuscaDao.getCodigo());
		locacao = locacaobuscaDao;
		return null;
	}


	@Transacional
	public void addEquipamento () {
		if(listaEquipamentos.isEmpty()) {
			listaEquipamentos = new ArrayList<Equipamento>();
		}
			listaEquipamentos.add(equipamento);
			equipamento = new Equipamento();
	}
	
	
	@Transacional
	public String incluir() {
		boolean existe = locacaoDao.existe(locacao);
		if (existe == false) {
			locacao.setStatusLocacao(StatusLocacao.Ativa);
			for(int i = 0; i<locacao.getEquipamentos().size();i++) {
				locacao.getEquipamentos().get(i).setStatus(StatusEquipamento.Locado);
			}
			locacaoDao.adiciona(locacao);
			mensagemSucesso("Cadastrado com sucesso");
			locacao = new Locacao();
			return null;
		}
		mensagemErro("O Codigo informado pertence a outra locação cadastrada");
		return null;
	}

	
	
	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	@Transacional
	public String atualizaLocacao() {
		locacaoDao.atualiza(locacao);
		mensagemSucesso("Atualizada Corretamente!");
		locacao = new Locacao();
		return null;
	}
	@Transacional
	public String excluirLocacao() {
		try {
			locacaoDao.remove(locacao);
			mensagemSucesso ("Excluido corretamente!");
		} catch (Exception e) {
			mensagemErro("Não foi possivel Excluir");
		}
		limpar();
		return null;
	}

	@Transacional
	public List<Locacao> locacaosCadastrados() {
		List<Locacao> locacao = new ArrayList<Locacao>();
		locacao = locacaoDao.listaTodosPaginada(0, 100);
		return locacao;
	}

	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}

	public String getCodBuscar() {
		return codBuscar;
	}

	public void setCodBuscar(String codBuscar) {
		this.codBuscar = codBuscar;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}	
	
	@Transacional
	public void buscaPorTag() {
		try {
			equipamento = new Equipamento();
			equipamento = equipamentoDao.buscaTag(tag);
			if (equipamento.getTag().isEmpty()) {
				mensagemErro("Este equipamento não foi localizado");
			}
		} catch (Exception e) {
			mensagemErro("Erro não foi possível localizar");
		}
	}

	public List<Equipamento> getListaEquipamentos() {
		return listaEquipamentos;
	}

	public void setListaEquipamentos(List<Equipamento> listaEquipamentos) {
		this.listaEquipamentos = listaEquipamentos;
	}

	

}