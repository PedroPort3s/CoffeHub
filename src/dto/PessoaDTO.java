package dto;

/*
 * DTO serve apenas para mover dados, geralmente usados para aumentar a segurança da aplicação, vc move uma pessoa 
 * sem mostrar o id e essas coisas
 */

public class PessoaDTO {
	private String nome;
	
	@Override
	public String toString() {
		return "PessoaDTO nome=" + nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
