package Negocio;

import java.util.List;

import Negocio.Beans.ItemProdutoEntity;
import dados.IItemProdutoDAO;
import dados.ItemProdutoDAO;

public class CItemProduto implements ICItemProduto {
	private static CItemProduto instance;
	private IItemProdutoDAO iitemP;
	
	private CItemProduto() {
		this.iitemP = ItemProdutoDAO.getInstance();
	}
	
	public static synchronized CItemProduto getInstance() {
		if (instance == null) {
			instance = new CItemProduto();
		}
		return instance;
	}
	
	public boolean cadastrarItemProduto(ItemProdutoEntity itemP) {
		return iitemP.cadastrarItemProduto(itemP);
	}
	
	public ItemProdutoEntity editarItemProduto(ItemProdutoEntity itemP) {
		return iitemP.editarItemProduto(itemP);
	}
	
	public void apagarItemProduto(int id, String codigo) {
		iitemP.apagarItemProduto(id, codigo);
	}
	
	public List<ItemProdutoEntity> consultarItemProduto(int id) {
		return iitemP.consultarItemProduto(id);
	}
	public List<ItemProdutoEntity> listarItemProduto() {
		return iitemP.listarItemProduto();
	}

}
