package entitys;

public class UnidadeMedida {
	private int id;
	private String cod;
	private String nome;
	private Boolean permiteFracionado;
	
	public int getId() {
		return id;
	}

	public UnidadeMedida(int id, String cod, String nome, Boolean permiteFracionado) {
		super();
		this.id = id;
		this.cod = cod;
		this.nome = nome;
		this.permiteFracionado = permiteFracionado;
	}
	
	public UnidadeMedida() {}


	public void setId(int id) {
		this.id = id;
	}

	public String getCod() {
		return cod;
	}
	
	public void setCod(String cod) {
		this.cod = cod;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getPermiteFracionado() {
		return permiteFracionado;
	}
	public void setPermiteFracionado(Boolean permiteFracionado) {
		this.permiteFracionado = permiteFracionado;
	}

	@Override
	public String toString() {
		return "UnidadeMedida: " + id + " - cod: " + cod + " - nome: " + nome + " - permiteFracionado: "
				+ permiteFracionado + "";
	}
	
	
	
}
