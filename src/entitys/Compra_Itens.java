package entitys;

public class Compra_Itens {

	private int num_item;

	private double qtd_item;

	private double valor_compra;

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

	public double getValor_compra() {
		return valor_compra;
	}

	public void setValor_compra(double valor_compra) {
		this.valor_compra = valor_compra;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	private Produto produto;
}
