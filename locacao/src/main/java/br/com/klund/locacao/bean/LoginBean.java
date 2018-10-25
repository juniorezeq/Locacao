package br.com.klund.locacao.bean;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.klund.locacao.tx.Transacional;
import br.com.klund.locacao.modelo.dao.UsuarioDao;
import br.com.klund.locacao.modelo.negocio.TipoUsuario;
import br.com.klund.locacao.modelo.negocio.Usuario;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final String USUARIO_LOGADO = "usuarioLogado";

	private static final long serialVersionUID = 1L;
	@Inject
	private HttpSession session;
	@Inject
	private UsuarioDao usuarioDao;
	private Usuario usuario;
	private String senhaNova1;
	private String senhaNova2;
	private String senhaAtualBD;
	private String senhaAtual;



	@PostConstruct
	public void init() {
		System.out.println("LoginBean.init();");
		usuario = (Usuario) session.getAttribute(USUARIO_LOGADO);
		if (usuario == null) {
			usuario = new Usuario();
		}
	}

	@Transacional
	public String perfil() {
		return "/view/perfil/perfil.xhtml";
	}

	@Transacional
	public String loga() {
		Usuario usuarioAutenticado = usuarioDao.buscaUsuarioPelaAutenticacao(this.usuario);
		if (usuarioAutenticado != null) {
			usuarioAutenticado.setDataDoUltimoAcesso(LocalDateTime.now());
			usuarioDao.atualiza(usuarioAutenticado);
			session.setAttribute(USUARIO_LOGADO, usuarioAutenticado);
			usuario = usuarioAutenticado;
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);
			return "/view/logado.xhtml?faces-redirect=true";
		}
		String mensagem = "Usuário ou senha inválido!";
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", mensagem));
		return null;
	}

	public Date ultimoAcesso() {
		Date date = asDate(usuario.getDataDoUltimoAcesso());
		return date;
	}

	public static Date asDate(LocalDateTime localDateTime) {
		return (Date) Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Transacional
	public String desloga() {
		session.removeAttribute(USUARIO_LOGADO);
		session.invalidate();
		usuario = new Usuario();
		return "/view/login/login.xhtml?faces-redirect=true";
	}

	@Transacional
	public List<Usuario> usuarios() {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = usuarioDao.listarTodos();
		return usuarios;
	}

	@Transacional
	public String novoUsuario() {
		return "/view/novousuario.xhtml?faces-redirect=true";
	}

	@Transacional
	public void atualizaUsuario() {
		try {
			usuarioDao.atualiza(usuario);
			String mensagem = "Alterado com sucesso";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!", mensagem));

		} catch (Exception e) {
			String mensagem = "Erro não foi possivel atualizar";
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro!", mensagem));

		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public static String getUsuarioLogado() {
		return USUARIO_LOGADO;
	}

	
	public String getSenhaNova1() {
		return senhaNova1;
	}

	public void setSenhaNova1(String senhaNova1) {
		this.senhaNova1 = senhaNova1;
	}

	public String getSenhaNova2() {
		return senhaNova2;
	}

	public void setSenhaNova2(String senhaNova2) {
		this.senhaNova2 = senhaNova2;
	}


	public String getSenhaAtualBD() {
		return senhaAtualBD;
	}

	public void setSenhaAtualBD(String senhaAtualBD) {
		this.senhaAtualBD = senhaAtualBD;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
