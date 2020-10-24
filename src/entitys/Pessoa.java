package entitys;

import java.text.ParseException;

import utils.Formatacao;

public abstract class Pessoa {

	private int cod;

	private String documento;

	private String telefone;

	private String nome;

	private String endereco;

	private String email;

	public Pessoa() {
		super();
	}

	public Pessoa(String documento, String telefone, String nome, String endereco, String email) {
		super();
		this.documento = documento;
		this.telefone = telefone;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
	}

	public Pessoa(int cod, String documento, String telefone, String nome, String endereco, String email) {
		super();
		this.cod = cod;
		this.documento = documento;
		this.telefone = telefone;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Pessoa [cod=" + cod + ", documento=" + Formatacao.formatarDocumento(documento) + ", telefone=" + telefone + ", nome=" + nome
				+ ", endereco=" + endereco + ", email=" + email + "]";
	}
}
