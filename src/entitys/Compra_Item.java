package entitys;

import java.util.List;

import control.produto.ControlProduto;

public class Compra_Item {

	private int num_item;

	private double qtd_item;

	private double valor_unitario;
	
	private Produto produto;

	public int getNum_item() {
		return num_item;
	}

	public void setNum_item(int num_item) {
		this.num_item = num_item;
	}

	public double getQtd_item() {
		return qtd_item;
	}

	public void setQtd_item(double qtd_item) {
		this.qtd_item = qtd_item;
	}

	public double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public static void ValidarCompraItens(List<Compra_Item> itens) throws Exception{
		if (itens != null) {
			for(Compra_Item item: itens) {
				ValidarCompraItemNumItem(item);	
			}
		}
	}
	
	public static void ValidarCompraItemNumItem(Compra_Item item) throws Exception{
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
	
	public static void ValidarCompraItem(Compra_Item item) throws Exception{
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

	@Override
	public String toString() {
		return "Nº item: " + num_item + " - qtd: " + qtd_item + " - valor un: " + valor_unitario + " - produto: " + produto;
	}
	
	
}
