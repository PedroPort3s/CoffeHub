package entitys;

public class Produto {
	
	public Produto() {
		
	}

	private int cod;

	private String descricao;

	private double valor_un;
	
	private int qtd_atual;

	private String unidadeMedida;

	private Categoria categoria;


	public Produto(String descricao, double valor_un, int qtd_atual, String unidadeMedida, Categoria categoria) {
		super();
		this.descricao = descricao;
		this.valor_un = valor_un;
		this.qtd_atual = qtd_atual;
		this.unidadeMedida = unidadeMedida;
		this.categoria = categoria;
	}
	
	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor_un() {
		return valor_un;
	}

	public void setValor_un(double valor_un) {
		this.valor_un = valor_un;
	}

	public int getQtd_atual() {
		return qtd_atual;
	}

	public void setQtd_atual(int qtd_atual) {
		this.qtd_atual = qtd_atual;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double valorTotalEstoque() {
		return 0;
	}

	public double quantidadeTotalEstoque() {
		return 0;
	}

	@Override
	public String toString() {
		return "Produto [cod=" + cod + ", descricao=" + descricao + ", valor_un=" + valor_un + ", qtd_atual="
				+ qtd_atual + ", unidadeMedida=" + unidadeMedida + ", categoria=" + categoria + "]";
	}	

}
