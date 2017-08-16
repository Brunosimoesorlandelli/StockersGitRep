package dados;

import java.util.List;

import Negocio.Beans.PedidoEntity;

public interface IPedidoDAO {
	
	public boolean cadastrarPedido(PedidoEntity pedido);
	public PedidoEntity editarPedido(PedidoEntity pedido);
	public PedidoEntity consultarPedido(int id);
	public List<PedidoEntity> listarPedido();

}
