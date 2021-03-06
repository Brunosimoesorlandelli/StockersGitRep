package dados;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import Negocio.Beans.ProdutoEntity;

public class ProdutoDAO implements IProdutoDAO {
	private static ProdutoDAO instance;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	private ProdutoDAO() {
		
	}
	
	public static synchronized ProdutoDAO getInstance() {
		if (instance == null) {
			instance = new ProdutoDAO();
		}
		return instance;
	}
	
	public EntityManager getEM(){
		emf = Persistence.createEntityManagerFactory("stockers");
		em = emf.createEntityManager();
		//emf.close();
		return em;
	}
	
	public boolean cadastrarProduto(ProdutoEntity produto) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		boolean retorno = false;
		
		//verifica se ainda n�o est� no banco?
		ProdutoEntity sproduto = em.find(ProdutoEntity.class, produto.getCodigo());
		
		if(sproduto == null) {
			//ent�o salva
			em.persist(produto);
			retorno = true;
		} else {
			System.out.println("Lancar excecao de produto ja existente!");
		}
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		return retorno;
	}
	
	public ProdutoEntity editarProduto(ProdutoEntity produto) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		em.merge(produto);
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		return produto;
	}
	
	//apagar do BD
	public void apagarProduto(String codigo) {
		EntityManager em = getEM();
			
		em.getTransaction().begin();
		
		//consultar BD
		ProdutoEntity produto = em.find(ProdutoEntity.class, codigo);
		
		em.remove(produto);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	//consultar do BD
	public ProdutoEntity consultarProduto(String nome) {
		List<ProdutoEntity> listaP = this.listarProduto();
		ProdutoEntity produto = null;
		
		for(ProdutoEntity p : listaP) {
			if(p.getNome().equals(nome)) {
				produto = p;
			}
		}
		
		return produto;
	}
	
	public List<ProdutoEntity> listarProduto() {
		EntityManager em = getEM();
		
		List<ProdutoEntity> listaP;
		
		String queryStr = "SELECT pj FROM ProdutoEntity pj"; //The query now changed to database independent
		
		Query query = em.createQuery(queryStr);
		listaP = query.getResultList();
		
		//System.out.println("Result Size: "+query.getResultList().size());
		
		em.close();
		emf.close();
		return listaP;
	}

}
