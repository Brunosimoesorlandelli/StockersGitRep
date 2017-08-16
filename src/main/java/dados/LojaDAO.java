package dados;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class LojaDAO implements ILojaDAO {
	
	private static LojaDAO instanceLoja;
	private EntityManagerFactory emf;
	private EntityManager em;
	
	private LojaDAO() {
		
	}
	
	public static synchronized LojaDAO getInstanceLoja() {
		if (instanceLoja == null) {
			instanceLoja = new LojaDAO();
		}
		return instanceLoja;
	}
	
	public EntityManager getEM(){
		emf = Persistence.createEntityManagerFactory("stockers");
		em = emf.createEntityManager();
		//emf.close();
		return em;
	}
	
	//salvar no BD
	public void cadastrarLoja(LojaEntity loja) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		LojaEntity sloja = em.find(LojaEntity.class, loja.getEmail()); 
		
		//verifica se ainda n�o est� no banco?
		//if(loja.getLojaEmail() == null) {
		if(sloja == null) {
			//ent�o salva
			em.persist(loja);
		} else {	//atualiza
			System.out.println("Lancar excecao de email ja existente!");
		}
		
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	//atualizar no BD
	public LojaEntity editarLoja(LojaEntity loja) {
		EntityManager em = getEM();
		em.getTransaction().begin();
		
		LojaEntity sloja = em.find(LojaEntity.class, loja.getEmail());
		
		if(sloja == null) {
			em.merge(loja);
		} else {
			if(loja.getEmail() == sloja.getEmail()) {
				em.merge(loja);
			} else {
				System.out.println("Lancar excecao de email ja existente!");
			}
		}
		
		em.getTransaction().commit();
		em.close();
		emf.close();
		return loja;
	}
	
	//apagar do BD
	public void apagarLoja(String email) {
		EntityManager em = getEM();
		
		em.getTransaction().begin();
		
		//consultar BD
		LojaEntity loja = em.find(LojaEntity.class, email);
		
		em.remove(loja);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}
	
	//consultar do BD
	public LojaEntity consultarLoja(String nome) {
		List<LojaEntity> listaL = this.listarLoja();
		LojaEntity loja = null;
		
		for(LojaEntity l : listaL) {
			if(l.getNomeEmpresa() == nome)
				loja = l;
		}
		
		return loja;
	}
	
	//metodo listarLoja
	public List<LojaEntity> listarLoja() {
		EntityManager em = getEM();
				
		List<LojaEntity> listaL;
		
		String queryStr = "SELECT * FROM stockers.loja"; //The query now changed to database independent
		Query query = em.createQuery(queryStr);
		listaL = query.getResultList();
		
		//System.out.println("Result Size: "+query.getResultList().size());
		
		em.close();
		emf.close();
		return listaL;
	}
	
	public LojaEntity efetuarLogin(String email, String senha) {
		EntityManager em = getEM();
		LojaEntity loja = null;
		
		loja = em.find(LojaEntity.class, email);
		
		if(loja.getSenha() != senha)
			loja = null;
		
		em.close();
		emf.close();
		return loja;
	}
	
}
