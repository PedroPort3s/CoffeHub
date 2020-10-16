package entitys;

import Helper.Verifica;

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
		
	
	public Produto(int cod, String descricao, double valor_un, int qtd_atual, String unidadeMedida,
			Categoria categoria) {
		super();
		this.cod = cod;
		this.descricao = descricao;
		this.valor_un = valor_un;
		this.qtd_atual = qtd_atual;
		this.unidadeMedida = unidadeMedida;
		this.categoria = categoria;
	}


	public Produto(String descricao, String valor_un, String qtd_atual, String unidadeMedida, Categoria categoriaSelecionada) {
		super();
		this.descricao = descricao;
		this.setValor_un(valor_un);
		this.setQtd_atual(qtd_atual);
		this.unidadeMedida = unidadeMedida;
		this.categoria = categoriaSelecionada;
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
	
	public void setValor_un(String valor_un) {
        if(Verifica.ehNumeroDouble(valor_un)) {
            this.valor_un = Double.parseDouble(valor_un);
        }
        else {
            this.valor_un = 0;
        }
    }

	public void setValor_un(double valor_un) {
		this.valor_un = valor_un;
	}

	public int getQtd_atual() {
		return qtd_atual;
	}

	
	public void setQtd_atual(String qtd_atual) {
        if(Verifica.ehNumeroInt(qtd_atual)) {
            this.qtd_atual = Integer.parseInt(qtd_atual);
        }
        else {
            this.qtd_atual = 0;
        }
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
		return "Produto: " + cod + " - " + descricao + " - valor unitário: " + valor_un + " - qtd atual: "
				+ qtd_atual + " - unidadeMedida: " + unidadeMedida + " - categoria: " + categoria;
	}	

}
