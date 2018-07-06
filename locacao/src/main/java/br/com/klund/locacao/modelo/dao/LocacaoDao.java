package br.com.klund.locacao.modelo.dao;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.klund.locacao.modelo.negocio.Equipamento;
import br.com.klund.locacao.modelo.negocio.Locacao;


@Named
@RequestScoped
public class LocacaoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Locacao> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Locacao>(this.em, Locacao.class);
	}

	@Inject
	private EntityManager em;

	public boolean existe(String codigo) {
		TypedQuery<Locacao> query = em.createQuery(" select u from Locacao u " + " where u.codigo = :pCodigo",
				Locacao.class);

		query.setParameter("pCodigo", codigo);
		try {
			@SuppressWarnings("unused")
			Locacao resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}
	
	public Locacao buscaCodigo(String codigo) {
		TypedQuery<Locacao> query = em.createQuery(
				  " select u from Locacao u "
				+ " where u.codigo = :pCodigo", Locacao.class);
		
		query.setParameter("pCodigo", codigo);
		try {
			@SuppressWarnings("unused")
			Locacao resultado = query.getSingleResult();
			return resultado;
		} catch (NoResultException ex) {
		return null;
		}
	}
	
	public Boolean existeLocacao(Locacao locacao) {
		TypedQuery<Locacao> query = em.createQuery(
				  " select u from Locacao u "
				+ " where u.codigo = :pCodigo", Locacao.class);
		
		query.setParameter("pCodigo", locacao.getCodigo());
		try {
			@SuppressWarnings("unused")
			Locacao resultado = query.getSingleResult();
			System.out.println("Existe a tag" + resultado.getCodigo());
			return true;
		} catch (NoResultException ex) {
		return false;
		}
	}
	
	public List<Locacao> listarTodos() {
		CriteriaQuery<Locacao> query = em.getCriteriaBuilder().createQuery(Locacao.class);
		query.select(query.from(Locacao.class));

		List<Locacao> lista = em.createQuery(query).getResultList();

		return lista;
	}
	
	public List<Locacao> locacoesAtivas() {
		TypedQuery<Locacao> query = em.createQuery(
				  " select u from Locacao u" + 
				  " where u.status = '0'", Locacao.class);
			try {
			List<Locacao> resultado = query.getResultList();
			return resultado;
		} catch (NoResultException ex) {
		return null;
		}
	}
	

	public void adiciona(Locacao locacao) {
		dao.adiciona(locacao);
	}

	public void atualiza(Locacao locacao) {
		em.merge(locacao);
	}

	public void remove(Locacao locacao) {
		dao.remove(locacao);
	}

	public Locacao buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Locacao> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

}
