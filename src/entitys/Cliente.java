package entitys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Cliente extends Pessoa {

	private Date data_nascimento;
	private int idade;

	public Cliente() {
		super();
	}

	public Cliente(Date data_nascimento, String documento, String telefone, String nome, String endereco,
			String email) {
		super(documento, telefone, nome, endereco, email);
		this.data_nascimento = data_nascimento;
		calcularIdade();
	}

	public Cliente(Date data_nascimento, String cod, String documento, String telefone, String nome, String endereco,
			String email) {
		super(cod, documento, telefone, nome, endereco, email);
		this.data_nascimento = data_nascimento;
		calcularIdade();
	}

	public int getIdade() {
		return idade;
	}

	private void calcularIdade() {
		SimpleDateFormat sdf = new SimpleDateFormat("y");
		idade = Integer.parseInt(sdf.format(new Date())) - Integer.parseInt(sdf.format(data_nascimento));
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public String getData_nascimentoString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(data_nascimento);
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	@Override
	public String toString() {
		return "Cliente [data_nascimento=" + data_nascimento + ", idade=" + idade + ", " + "cod=" + getCod()
				+ ", documento=" + getDocumento() + ", telefone=" + getTelefone() + ", nome=" + getNome()
				+ ", endereco=" + getEndereco() + ", email=" + getEmail() + "]";
	}
}
