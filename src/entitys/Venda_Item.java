package entitys;

public class Venda_Item {

	private int num_item;

	private Double qtd_item;

	private Double valor_venda;

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

	private Produto produto;
}
