package dto;

public class attProdutoDTO {
    private int codProduto;
    
    private String nomeProduto;

    private double qtdProduto;

    public attProdutoDTO(int codProduto, double qtdProduto) {
        super();
        this.codProduto = codProduto;
        this.qtdProduto = qtdProduto;
    }
    
    public attProdutoDTO(int codProduto, String nomeProduto, double qtdProduto) {
		super();
		this.codProduto = codProduto;
		this.nomeProduto = nomeProduto;
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

    public String getNomeProduto() {
		return nomeProduto;
	}

	

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public double getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(int qtdProduto) {
        this.qtdProduto = qtdProduto;
    }
}