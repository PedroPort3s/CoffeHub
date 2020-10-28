package entitys;

import java.util.List;

import control.produto.ControlProduto;

public class Venda_Item {

	private int num_item;

	private Double qtd_item;

	private Double valor_venda;

	private Produto produto;

	public int getNum_item() {
		return num_item;
	}

	public void setNum_item(int num_item) {
		this.num_item = num_item;
	}

	public Double getQtd_item() {
		return qtd_item;
	}

	public void setQtd_item(Double qtd_item) {
		this.qtd_item = qtd_item;
	}

	public Double getValor_venda() {
		return valor_venda;
	}

	public void setValor_venda(Double valor_venda) {
		this.valor_venda = valor_venda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public static void ValidarVendaItens(List<Venda_Item> itens) throws Exception{
		if (itens != null) {
			for(Venda_Item item: itens) {
				ValidarVendaItemNumItem(item);	
			}
		}
	}
	
	public static void ValidarVendaItemNumItem(Venda_Item item) throws Exception{
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
		if(item.getValor_venda() <=0)
			throw new Exception("Valor invalido");
	}
	
	public static void ValidarVendaItem(Venda_Item item) throws Exception{
		if(item == null)
			throw new Exception("Item invalido");
		if(item.getProduto() != null)
			new ControlProduto().ValidarProdutoId(item.getProduto());
		else 
			throw new Exception("Produto não informado");
		if(item.getQtd_item() <= 0)
			throw new Exception("Quantidade invalida");
		if(item.getValor_venda() <=0)
			throw new Exception("Valor invalido");
	}
}
