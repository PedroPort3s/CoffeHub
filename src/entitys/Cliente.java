package entitys;

import java.util.Date;

public class Cliente extends Pessoa {

	private Date data_nascimento;

	public int calcularIdade() {
		return 0;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

}
