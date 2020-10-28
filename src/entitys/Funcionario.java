package entitys;

import java.time.LocalDate;

import com.sun.istack.internal.Nullable;

import utils.Formatacao;


public class Funcionario extends Pessoa {

	private LocalDate data_contratacao;
	private LocalDate data_demissao;
	private double salario;
	private String senha_funcionario;
	private int cod_acesso;

	public Funcionario() {
		super();
	}

	public Funcionario(LocalDate data_contratacao, @Nullable LocalDate data_demissao, double salario, String senha_funcionario,
			int cod_acesso, String documento, String telefone, String nome, String endereco, String email) {
		super(documento, telefone, nome, endereco, email);
		this.data_contratacao = data_contratacao;
		this.data_demissao = data_demissao;
		this.salario = salario;
		this.senha_funcionario = senha_funcionario;
		this.cod_acesso = cod_acesso;
	}

	public Funcionario(LocalDate data_contratacao, @Nullable LocalDate data_demissao, double salario, String senha_funcionario,
			int cod_acesso, int cod, String documento, String telefone, String nome, String endereco, String email) {
		super(cod, documento, telefone, nome, endereco, email);
		this.data_contratacao = data_contratacao;
		this.data_demissao = data_demissao;
		this.salario = salario;
		this.senha_funcionario = senha_funcionario;
		this.cod_acesso = cod_acesso;
	}

	public int getCod_acesso() {
		return cod_acesso;
	}

	public void setCod_acesso(int cod_acesso) {
		this.cod_acesso = cod_acesso;
	}

	public String getSenha_funcionario() {
		return senha_funcionario;
	}

	public void setSenha_funcionario(String senha_funcionario) {
		this.senha_funcionario = senha_funcionario;
	}

	public LocalDate getData_contratacao() {
		return data_contratacao;
	}

	public void setData_contratacao(LocalDate data_contratacao) {
		this.data_contratacao = data_contratacao;
	}

	public LocalDate getData_demissao() {
		return data_demissao;
	}

	public void setData_demissao(LocalDate data_demissao) {
		this.data_demissao = data_demissao;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		if(data_demissao == null) {
			return "Funcionario: " + getCod() + " - nome: " + getNome() + " - documento: " + Formatacao.formatarDocumento(getDocumento()) + 
					" - telefone: " + Formatacao.formatarTelefone(getTelefone()) + " - data contratação: " + data_contratacao + " - endereço: " + getEndereco() + " - salario: " + salario +" - email: " + getEmail();
		} else {
			return "Funcionario: " + getCod()  + " - nome: " + getNome() + " - documento: " + Formatacao.formatarDocumento(getDocumento()) + " - telefone: " + Formatacao.formatarTelefone(getTelefone())
			+ " - data contratação: " + data_contratacao + " - data demissão: " + data_demissao + " - endereço: " + getEndereco() + " - salario: " + salario + " - email: " + getEmail();
		}
			
		
	}
}
























