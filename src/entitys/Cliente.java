package entitys;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import utils.Formatacao;

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

	@Override
	public String toString() {
		DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return "Cliente: cod = " + getCod() + ", idade = " + idade + ", nome=" + getNome()
				+ ", documento=" + Formatacao.formatarDocumento(getDocumento()) + ", telefone="
				+ Formatacao.formatarTelefone(getTelefone()) + ", endereco=" + getEndereco() + ", email=" + getEmail()
				+ "]";
	}
}
