package br.com.klund.locacao.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.klund.locacao.modelo.dao.EquipamentoDao;
import br.com.klund.locacao.modelo.dao.FornecedorDao;
import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.modelo.negocio.Fornecedor;
import br.com.klund.locacao.modelo.negocio.Usuario;
import br.com.klund.locacao.tx.Transacional;
import br.com.klund.locacao.validador.EquipamentoValidador;

import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class EquipamentoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EquipamentoDao equipamentoDao = new EquipamentoDao();
	@Inject
	private Equipamento equipamento = new Equipamento();
	@Inject
	private Equipamento copia = new Equipamento();
	@Inject
	private Equipamento selecionado = new Equipamento();
	@Inject
	private Usuario usuarioLogado = new Usuario();
	private String buscar;
	private boolean proprio;
	private String buscarFornecedor;
	@Inject
	private EquipamentoValidador equipamentoValidador;
	@Inject
	private Fornecedor fornecedor;
	@Inject
	private FornecedorDao fornecedorDao;




	@PostConstruct
	public void init() {
		equipamento = new Equipamento();
		usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	}
	
	 

	@Transacional
	public String iniciarCadastro() {
		return "/view/cadastro/cadastrarequipamento.xhtml?faces-redirect=true";
	}

	@Transacional
	public String alterarCadastro() {
		return "/view/cadastro/editarequipamento.xhtml?faces-redirect=true";
	}

	@Transacional
	public String listarCadastro() {
		return "/view/cadastro/listarequipamento.xhtml?faces-redirect=true";
	}

	@Transacional
	public List<Equipamento> listarTodos() {
		List<Equipamento> lista = new ArrayList<Equipamento>();
		lista = equipamentoDao.listarTodos();
		return lista;
	}
	
   

	@Transacional
	public List<Equipamento> listarDisponiveis() {
		List<Equipamento> lista = new ArrayList<Equipamento>();
		lista = equipamentoDao.equipamentosDisponiveis();
		return lista;
	}

	@Transacional
	public String checarCnpj() {
		Fornecedor fornecedorbuscaDao = fornecedorDao.buscaCnpj(buscarFornecedor);
		if (fornecedorbuscaDao == null) {
			mensagemErro("Fornecedor não foi encontrado verifique o CNPJ digitado");
			return null;
		}
		System.out.println(fornecedorbuscaDao.getCnpj());
		fornecedor = fornecedorbuscaDao;
		return null;
	}

	@Transacional
	public String incluir() {
		equipamento.setFornecedor(fornecedor);
		equipamento.setUltimaAlteracao(usuarioLogado.getNome());
		if (equipamentoValidador.naoPodeIncluir(equipamento)) {
			mensagemErro(equipamentoValidador.getMensagem());
			return null;
		} else {
			equipamentoDao.adiciona(equipamento);
			equipamento = new Equipamento();
			fornecedor = new Fornecedor();
			buscar = "";
			mensagemSucesso("cadastrado com sucesso.");
			return null;
		}
	}

	@Transacional
	public List<Equipamento> equipamentosCadastrados() {
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		equipamentos = equipamentoDao.listaTodosPaginada(0, 100);
		return equipamentos;
	}

	@Transacional
	public void buscaPorTag() {
		try {
			equipamento = new Equipamento();
			equipamento = equipamentoDao.buscaTag(buscar);
			if (equipamento.getTag().isEmpty()) {
				mensagemErro("Este Equipamento não foi localizado");
			}
		} catch (Exception e) {
			mensagemErro("Erro não foi possível localizar");
		}
	}

	@Transacional
	public void limpar() {
		equipamento = new Equipamento();
		copia = new Equipamento();
		buscar = "";
		buscarFornecedor = "";
		proprio = false;
		equipamentoProprio();

	}

	@Transacional
	public void copiarPorTag() {
		try {
			copia = new Equipamento();
			copia = equipamentoDao.buscaTag(buscar);
			if (copia.getTag().isEmpty()) {
				mensagemErro("Este Equipamento não foi localizado");
			} else {
				equipamento.setDescricao(copia.getDescricao());
				equipamento.setElevacao(copia.getElevacao());
				equipamento.setFornecedor(copia.getFornecedor());
				equipamento.setModelo(copia.getModelo());
				equipamento.setSwl(copia.getSwl());
				equipamento.setPrecoDiariaMensal(copia.getPrecoDiariaMensal());
				equipamento.setPrecoDiariaQuinzenal(copia.getPrecoDiariaQuinzenal());
				equipamento.setPastaCertificados(copia.getPastaCertificados());
				equipamento.setValorNota(copia.getValorNota());
				equipamento.setFornecedor(copia.getFornecedor());
			}
		} catch (Exception e) {
			mensagemErro("Erro não foi possível localizar");
		}
	}
	
	@Transacional
	public void atualizarVencimento() {
		equipamento.setValidadeCertificacao(equipamento.getDataCertificacao().plusYears(1));
	}

	@Transacional
	public void atualizaEquipamento() {
		equipamento.setUltimaAlteracao(usuarioLogado.getNome());
		try {
			equipamentoDao.atualiza(equipamento);
			mensagemSucesso("Alterado com sucesso");
			equipamento = new Equipamento();
			buscar = "";
			equipamento = new Equipamento();
		} catch (Exception e) {
			mensagemErro("Erro não foi possivel atualizar");
		}
	}

	@Transacional
	public void apagarEquipamento() {
		try {
			equipamentoDao.remove(equipamento);
			mensagemSucesso("apagado com sucesso");
			equipamento = new Equipamento();
			buscar = "";

		} catch (Exception e) {
			mensagemErro("Erro não foi possivel apagar");
		}
	}

	private void mensagemSucesso(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));
	}

	private void mensagemErro(String mensagem) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

	}

	public String getBuscar() {
		return buscar;
	}

	public void setBuscar(String buscar) {
		this.buscar = buscar;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Equipamento getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(Equipamento selecionado) {
		this.selecionado = selecionado;
	}

	public void onSelect(Equipamento equipamento) {
		selecionado = new Equipamento();
		selecionado = equipamento;
		System.out.println(selecionado.getTag());
	}

	public void onDeselect(Equipamento equipamento) {
		equipamento = new Equipamento();
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public String getBuscarFornecedor() {
		return buscarFornecedor;
	}

	public void setBuscarFornecedor(String buscarFornecedor) {
		this.buscarFornecedor = buscarFornecedor;
	}

	public void equipamentoProprio() {
		if (proprio) {
			buscarFornecedor = "07.485.047/0001-31";
			Fornecedor fornecedorbuscaDao = fornecedorDao.buscaCnpj("07.485.047/0001-31");
			fornecedor = fornecedorbuscaDao;
		}
		if (proprio == false) {
			buscarFornecedor = "";
			fornecedor = new Fornecedor();
		}

	}

	public boolean isProprio() {
		return proprio;
	}

	public void setProprio(boolean proprio) {
		this.proprio = proprio;
	}

	   
	
}
