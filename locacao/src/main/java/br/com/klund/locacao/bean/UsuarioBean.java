package br.com.klund.locacao.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.klund.locacao.modelo.dao.UsuarioDao;
import br.com.klund.locacao.modelo.negocio.TipoUsuario;
import br.com.klund.locacao.modelo.negocio.Usuario;
import br.com.klund.locacao.tx.Transacional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Named
@ViewScoped
public class UsuarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{loginBean}" )
	@Inject
	private UsuarioDao usuarioDao = new UsuarioDao();
	@Inject
	private Usuario usuario = new Usuario();
	private String buscar;
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}
	
	@Transacional
	public String alterarSenha() {
		return "/view/cadastro/alterarsenha.xhtml?faces-redirect=true";
		}

	@Transacional
	public String iniciarCadastro() {
		if(LoginBean.getLogado().getTipoUsuario()!=TipoUsuario.Administrador) {
			return "/view/naoautorizado.xhtml?faces-redirect=true";
		}else {
			return "/view/cadastro/cadastrarusuario.xhtml?faces-redirect=true";
		}
	}

	@Transacional
	public String alterarCadastro() {
		if(LoginBean.getLogado().getTipoUsuario()!=TipoUsuario.Administrador) {
			return "/view/naoautorizado.xhtml?faces-redirect=true";
		}else {
			return "/view/cadastro/editarusuario.xhtml?faces-redirect=true";
		}
		
	}

	@Transacional
	public String listarCadastro() {
		return "/view/cadastro/listarusuarios.xhtml?faces-redirect=true";
	}

	@Transacional
	public List<Usuario> listarTodos() {
		List<Usuario> lista = new ArrayList<Usuario>();
		lista = usuarioDao.listarTodos();
		return lista;
	}

	@Transacional
	public String incluir() {
		usuario.setDataDoCadastro(LocalDateTime.now());
		if (usuario.getLogin().isEmpty() || usuario.getSenha().isEmpty()) {
			mensagemErro("Todos os campos devem ser preenchidos");
			return null;
		}if(LoginBean.getLogado().getTipoUsuario()!=TipoUsuario.Administrador) {
			mensagemErro("Você não tem permissão para usar este recurso");
			return null;
		}else {
			boolean existe = usuarioDao.existeLogin(usuario.getLogin());
			if (existe == false) {
				usuarioDao.adiciona(usuario);
				usuario = new Usuario();
				mensagemSucesso("Cadastrado com sucesso");
				return null;
			}
			mensagemErro("Não foi possivel realizar o cadastro usuário já existe");
			return null;
			
		}
	
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Transacional
	public List<Usuario> usuariosCadastrados() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioDao.listaTodosPaginada(0, 100);
		return usuarios;
	}

	@Transacional
	public void buscaPorNome() {
		try {
			usuario = new Usuario();
			usuario = usuarioDao.BuscaLogin(buscar);
		if (usuario.getLogin().isEmpty()) {
			mensagemErro("Este usuário não foi localizado");
		}
		} catch (Exception e) {
			mensagemErro("Erro não foi possível localizar");

		}
	}

	@Transacional
	public void atualizaUsuario() {
		try {
			usuarioDao.atualiza(usuario);
			mensagemSucesso("Alterado com sucesso");
			usuario = new Usuario();
			buscar ="";
			} catch (Exception e) {
			mensagemErro("Erro não foi possivel atualizar");
		}
	}

	@Transacional
	public void apagarUsuario() {
		try {
			usuarioDao.remove(usuario);
			mensagemSucesso("apagado com sucesso");
			usuario = new Usuario();
			buscar = "";

		} catch (Exception e) {
			mensagemErro("Erro não foi possivel apagar");
		}
	}

	@Transacional
	public void gerarSenha() {
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		String temp = myRandom.substring(31);
		usuario.setSenha(temp);
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
	
	 public void destroyWorld() {
	       apagarUsuario();
	    }
	     
	 }
