package control.compra_venda;

import java.util.List;

import control.produto.ControlProduto;
import entitys.Compra_Itens;

public class ControlCompraItens {

	
	public void ValidarCompraItens(List<Compra_Itens> itens) throws Exception{
		if (itens != null) {
			for(Compra_Itens item: itens) {
				ValidarCompraItemNumItem(item);	
			}
		}
	}
	
	private void ValidarCompraItemNumItem(Compra_Itens item) throws Exception{
		if(item == null)
			throw new Exception("Item invalido");
		if(item.getNum_item() <= 0)
			throw new Exception("Item invalido");
		if(item.getProduto() != null)
			new ControlProduto().ValidarProdutoId(item.getProduto());
		else 
			throw new Exception("Produto não informado");
		if(item.getQtd_item() <= 0)
			throw new Exception("Quantidade invalida");
		if(item.getValor_unitario() <=0)
			throw new Exception("Valor invalido");
	}
	
	private void ValidarCompraItem(Compra_Itens item) throws Exception{
		if(item == null)
			throw new Exception("Item invalido");
		if(item.getProduto() != null)
			new ControlProduto().ValidarProdutoId(item.getProduto());
		else 
			throw new Exception("Produto não informado");
		if(item.getQtd_item() <= 0)
			throw new Exception("Quantidade invalida");
		if(item.getValor_unitario() <=0)
			throw new Exception("Valor invalido");
	}
}
