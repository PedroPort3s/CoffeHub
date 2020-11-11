package dto;

public class attProdutoDTO {
	private int codProduto;
	
	private double qtdProduto;

	public attProdutoDTO(int codProduto, double qtdProduto) {
		super();
		this.codProduto = codProduto;
		this.qtdProduto = qtdProduto;
	}

	@Override
	public String toString() {
		return "attProdutoDTO [codProduto=" + codProduto + ", qtdProduto=" + qtdProduto + "]";
	}

	public int getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}

	public double getQtdProduto() {
		return qtdProduto;
	}

	public void setQtdProduto(int qtdProduto) {
		this.qtdProduto = qtdProduto;
	}
}
