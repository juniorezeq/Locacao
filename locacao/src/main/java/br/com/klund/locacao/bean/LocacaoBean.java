package br.com.klund.locacao.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.klund.locacao.modelo.dao.ClienteDao;
import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.dao.LocacaoDao;
import br.com.klund.locacao.modelo.negocio.Cliente;
import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.modelo.negocio.Locacao;
import br.com.klund.locacao.modelo.negocio.StatusEquipamento;
import br.com.klund.locacao.modelo.negocio.StatusLocacao;
import br.com.klund.locacao.tx.Transacional;

@Named
@ViewScoped
public class LocacaoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LocacaoDao locacaoDao = new LocacaoDao();
	@Inject
	private Locacao locacao = new Locacao();
	@Inject
	private Cliente cliente;
	@Inject
	private Cliente selecionado;
	private String codBuscar;
	private String buscarCliente;
	private String tag;
	@Inject
	private Equipamento equipamento = new Equipamento();
	@Inject
	private EquipamentoDao equipamentoDao = new EquipamentoDao();
	@Inject
	private ClienteDao clienteDao = new ClienteDao();
	private List<Equipamento> listaEquipamentos = new ArrayList<Equipamento>();

	@PostConstruct
	public void init() {
		System.out.println("LocacaoBean.init();");
		locacao = new Locacao();
	}

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastro/cadastrolocacao.xhtml?faces-redirect=true";
	}

	@Transacional
	public String listarLocacao() {
		return "/view/cadastro/listalocacoes.xhtml?faces-redirect=true";
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
	public void addEquipamento() {
		if (listaEquipamentos.isEmpty()) {
			listaEquipamentos = new ArrayList<Equipamento>();
		}
		listaEquipamentos.add(equipamento);
		equipamento = new Equipamento();
	}
	
	@Transacional
	public List<Cliente> clientesCadastrados() {
		List<Cliente> lista = new ArrayList<Cliente>();
		lista = clienteDao.listarTodos();
		return lista;
	}

	
	
	public Cliente getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Cliente selecionado) {
		this.selecionado = selecionado;
	}

	@Transacional
	public String incluir() {
		boolean existe = locacaoDao.existe(locacao.getCodigo());
		if (existe == false) {
			locacao.setCliente(cliente);
			locacao.setStatusLocacao(StatusLocacao.Ativa);
		    alugarEquipamento();
			locacao.setEquipamentos(listaEquipamentos);
			locacaoDao.adiciona(locacao);
			mensagemSucesso("Cadastrado com sucesso");
			locacao = new Locacao();
			return null;
		}
		mensagemErro("O Codigo informado pertence a outra locação cadastrada");
		return null;
	}

	public void alugarEquipamento() {
		for (int i = 0; i< listaEquipamentos.size(); i++) {
			listaEquipamentos.get(i).setStatus(StatusEquipamento.Alugado);
			equipamentoDao.atualiza(listaEquipamentos.get(i));
		}
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
	public void selecionarCliente(Cliente clienteRec) {
		this.cliente = clienteRec;
	}
	
	@Transacional
	public List<Locacao> listarAtivas(){
		List<Locacao> ativas = new ArrayList<Locacao>();
		ativas = locacaoDao.locacoesAtivas();
		return ativas;
	}

	@Transacional
	public String excluirLocacao() {
		try {
			locacaoDao.remove(locacao);
			mensagemSucesso("Excluido corretamente!");
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
			equipamento = equipamentoDao.buscaTag(tag);
			if (equipamento.getTag().isEmpty()) {
				mensagemErro("Este equipamento não foi localizado");
			} else {
				System.out.println(equipamento.toString());
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getBuscarCliente() {
		return buscarCliente;
	}

	public void setBuscarCliente(String buscarCliente) {
		this.buscarCliente = buscarCliente;
	}
	
	public void onSelect(Cliente clienterec) {
		cliente = clienterec;
		System.out.println(cliente.getNome() + " recebido");
	}

	public void onDeselect(Cliente recebido) {
		cliente = new Cliente();
	}
	

	@Transacional
	public String checarCnpj() {
		Cliente clientebuscaDao = clienteDao.buscaCnpj(buscarCliente);
		if (clientebuscaDao == null) {
			mensagemErro("Cliente não foi encontrado verifique o CNPJ digitado");			
            return null;
            }
		System.out.println(clientebuscaDao.getCnpj());
		cliente = clientebuscaDao;
		return null;
	}
	
	
}