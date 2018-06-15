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

@Named
@RequestScoped
public class EquipamentoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Equipamento> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Equipamento>(this.em, Equipamento.class);
	}

	@Inject
	private EntityManager em;

	public boolean existe(Equipamento equipamento) {

		TypedQuery<Equipamento> query = em.createQuery(" select u from Equipamento u " + " where u.tag = :pTag",
				Equipamento.class);

		query.setParameter("pTag", equipamento.getTag());
		try {
			@SuppressWarnings("unused")
			Equipamento resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}
	
	public Equipamento buscaTag(String tag) {
		TypedQuery<Equipamento> query = em.createQuery(
				  " select u from Equipamento u "
				+ " where u.tag = :pTag", Equipamento.class);
		
		query.setParameter("pTag", tag);
		try {
			@SuppressWarnings("unused")
			Equipamento resultado = query.getSingleResult();
			return resultado;
		} catch (NoResultException ex) {
		return null;
		}
	}
	public boolean existeTag(String tag) {
		TypedQuery<Equipamento> query = em.createQuery(
				  " select u from Equipamento u "
				+ " where u.tag = :pTag", Equipamento.class);
		
		query.setParameter("pTag", tag);
		try {
			@SuppressWarnings("unused")
			Equipamento resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}

	public List<Equipamento> listarTodos() {
		CriteriaQuery<Equipamento> query = em.getCriteriaBuilder().createQuery(Equipamento.class);
		query.select(query.from(Equipamento.class));

		List<Equipamento> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public void adiciona(Equipamento equipamento) {
		dao.adiciona(equipamento);
	}

	public void atualiza(Equipamento equipamento) {
		em.merge(equipamento);
	}

	public void remove(Equipamento equipamento) {
		dao.remove(equipamento);
	}

	public Equipamento buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Equipamento> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

}
