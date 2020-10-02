package entitys;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class Funcionario extends Pessoa {

	private Date data_contratacao;

	private Date data_demissao;

	private double salario;
	
	
	
	
	public Funcionario() {
		super();
	}

	
	

	public Date getData_contratacao() {
		return data_contratacao;
	}

	public void setData_contratacao(Date data_contratacao) {
		this.data_contratacao = data_contratacao;
	}

	public Date getData_demissao() {
		return data_demissao;
	}

	public void setData_demissao(Date data_demissao) {
		this.data_demissao = data_demissao;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public int calcularIdade() {
		return 0;
	}

	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "Funcionario [data_contratacao=" + data_contratacao + ", data_demissao=" + data_demissao + ", salario="
				+ salario + "]";
	}

	
	

}
