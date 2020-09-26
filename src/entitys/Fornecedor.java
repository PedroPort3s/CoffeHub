package entitys;

import java.util.Date;

public class Fornecedor extends Pessoa {

	private Date data_contrato;

	private Date data_nascimento;

	public Date getData_contrato() {
		return data_contrato;
	}

	public void setData_contrato(Date data_contrato) {
		this.data_contrato = data_contrato;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public int calcularIdade() {
		return 0;
	}
}
