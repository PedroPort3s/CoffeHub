package entitys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utils.Formatacao;

public class Fornecedor extends Pessoa {

	private LocalDate data_contrato;

	public Fornecedor() {
		super();
	}

	@Override
	public String toString() {
		DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		return "Fornecedor: " + getCod() + " - nome: " + getNome() + " - documento: " + Formatacao.formatarDocumento(getDocumento()) + " - telefone: " + Formatacao.formatarTelefone(getTelefone())
				+ " - endereco: " + getEndereco() + " - email: " + getEmail() + " - data contratação: " + data_contrato.format(formatar);	
		}
	
	public Fornecedor(LocalDate data_contrato, String documento, String telefone, String nome, String endereco,
			String email) {
		super(documento, telefone, nome, endereco, email);
		this.data_contrato = data_contrato;
	}

	public Fornecedor(LocalDate data_contrato, int cod, String documento, String telefone, String nome, String endereco,
			String email) {
		super(cod, documento, telefone, nome, endereco, email);
		this.data_contrato = data_contrato;
	}

	public LocalDate getData_contrato() {
		return data_contrato;
	}

	public void setData_contrato(LocalDate data_contrato) {
		this.data_contrato = data_contrato;
	}
}
