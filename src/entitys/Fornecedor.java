package entitys;

import java.time.LocalDate;

import utils.Formatacao;

public class Fornecedor extends Pessoa {

	private LocalDate data_contrato;

	public Fornecedor() {
		super();
	}

	@Override
	public String toString() {
		return "Fornecedor [cod=" + getCod() + ", nome=" + getNome() + ", documento=" + Formatacao.formatarDocumento(getDocumento()) + ", telefone=" + getTelefone()
				+ ", endereco=" + getEndereco() + ", email=" + getEmail() + "]";	
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
