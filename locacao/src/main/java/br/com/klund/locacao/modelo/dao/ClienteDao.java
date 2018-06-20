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

import br.com.klund.locacao.modelo.negocio.Cliente;

@Named
@RequestScoped
public class ClienteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Cliente> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Cliente>(this.em, Cliente.class);
	}

	@Inject
	private EntityManager em;

	public boolean existeCnpj(Cliente cliente) {

		TypedQuery<Cliente> query = em.createQuery(" select u from Cliente u " + " where u.cnpj = :pCnpj",
				Cliente.class);

		query.setParameter("pCnpj", cliente.getCnpj());
		try {
			@SuppressWarnings("unused")
			Cliente resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}
	
	public Cliente buscaCnpj(String cnpj) {
		TypedQuery<Cliente> query = em.createQuery(
				  " select u from Cliente u "
				+ " where u.cnpj = :pCnpj", Cliente.class);
		
		query.setParameter("pCnpj", cnpj);
		try {
			@SuppressWarnings("unused")
			Cliente resultado = query.getSingleResult();
			return resultado;
		} catch (NoResultException ex) {
		return null;
		}
	}
	
		
	public List<Cliente> listarTodos() {
		CriteriaQuery<Cliente> query = em.getCriteriaBuilder().createQuery(Cliente.class);
		query.select(query.from(Cliente.class));

		List<Cliente> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public void adiciona(Cliente cliente) {
		dao.adiciona(cliente);
	}

	public void atualiza(Cliente cliente) {
		em.merge(cliente);
	}

	public void remove(Cliente cliente) {
		dao.remove(cliente);
	}

	public Cliente buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Cliente> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

}
