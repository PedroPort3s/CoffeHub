package entitys;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Cliente extends Pessoa {

	private LocalDate data_nascimento;
	private int idade;

	public Cliente() {
		super();
	}

	public Cliente(LocalDate data_nascimento, String documento, String telefone, String nome, String endereco,
			String email) {
		super(documento, telefone, nome, endereco, email);
		this.data_nascimento = data_nascimento;
		calcularIdade();
	}

	public Cliente(LocalDate data_nascimento, int cod, String documento, String telefone, String nome, String endereco,
			String email) {
		super(cod, documento, telefone, nome, endereco, email);
		this.data_nascimento = data_nascimento;
		calcularIdade();
	}

	public int getIdade() {
		return idade;
	}

	private void calcularIdade() {
		Period periodo = Period.between(data_nascimento, LocalDate.now());
		idade = periodo.getYears();
	}

	public LocalDate getData_nascimento() {
		return data_nascimento;
	}

	public String getData_nascimentoString() {
		DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data_nascimento.format(dataFormat);
	}

	public void setData_nascimento(LocalDate data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String toStringResumido() {
		return "Cliente [cod=" + getCod() + " data_nascimento=" + getData_nascimentoString() + ", idade=" + idade
		+ ", documento=" + getDocumento() + ", telefone=" + getTelefone() + ", nome=" + getNome().substring(0, 10)
		+ ", endereco=" + getEndereco().substring(0, 10) + ", email=" + getEmail() + "]";
	}
	
	@Override
	public String toString() {
		return "Cliente: cod = " + getCod() + ", data_nascimento = " + data_nascimento + ", nome=" + getNome() + ", documento=" + getDocumento() + ", telefone=" + getTelefone()
				+ ", endereco=" + getEndereco() + ", email=" + getEmail() + "]";
	}
}
