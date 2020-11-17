package entitys;

public class UnidadeMedida {
	private int id;
	private String cod;
	private String nome;
	private Boolean permiteFracionado;
	
	public int getId() {
		return id;
	}

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
}
