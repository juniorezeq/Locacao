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

import br.com.klund.locacao.modelo.negocio.Fornecedor;

@Named
@RequestScoped
public class FornecedorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	private DAO<Fornecedor> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Fornecedor>(this.em, Fornecedor.class);
	}

	@Inject
	private EntityManager em;

	public boolean existeCnpj(Fornecedor fornecedor) {

		TypedQuery<Fornecedor> query = em.createQuery(" select u from Fornecedor u " + " where u.cnpj = :pCnpj",
				Fornecedor.class);

		query.setParameter("pCnpj", fornecedor.getCnpj());
		try {
			@SuppressWarnings("unused")
			Fornecedor resultado = query.getSingleResult();
			return true;
		} catch (NoResultException ex) {
			return false;
		}
	}
	
	public Fornecedor buscaCnpj(String cnpj) {
		TypedQuery<Fornecedor> query = em.createQuery(
				  " select u from Fornecedor u "
				+ " where u.cnpj = :pCnpj", Fornecedor.class);
		
		query.setParameter("pCnpj", cnpj);
		try {
			@SuppressWarnings("unused")
			Fornecedor resultado = query.getSingleResult();
			return resultado;
		} catch (NoResultException ex) {
		return null;
		}
	}
	
		
	public List<Fornecedor> listarTodos() {
		CriteriaQuery<Fornecedor> query = em.getCriteriaBuilder().createQuery(Fornecedor.class);
		query.select(query.from(Fornecedor.class));

		List<Fornecedor> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public void adiciona(Fornecedor fornecedor) {
		dao.adiciona(fornecedor);
	}

	public void atualiza(Fornecedor fornecedor) {
		em.merge(fornecedor);
	}

	public void remove(Fornecedor fornecedor) {
		dao.remove(fornecedor);
	}

	public Fornecedor buscaPorId(Long id) {
		return dao.buscaPorId(id);
	}

	public List<Fornecedor> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

}
