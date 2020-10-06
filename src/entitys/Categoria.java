package entitys;

public class Categoria {

	private int cod;

	private String nome;

	public Categoria(int codCategoria, String nomeCategoria) {
		// TODO Auto-generated constructor stub
	}	

	public Categoria(String nome) {
		super();
		this.nome = nome;
	}
	
	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
