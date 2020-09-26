package entitys;

import java.util.Date;
import java.util.List;

public class Compra {

	private int cod;

	private Date data_origem;

	private Date data_recebido;

	private String status;

	private Fornecedor fornecedor;

	private Funcionario funcionario;

	private List<Compra_Itens> itens;

	public double TotalCompra(){
		return 0;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public Date getData_origem() {
		return data_origem;
	}

	public void setData_origem(Date data_origem) {
		this.data_origem = data_origem;
	}

	public Date getData_recebido() {
		return data_recebido;
	}

	public void setData_recebido(Date data_recebido) {
		this.data_recebido = data_recebido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Compra_Itens> getItens() {
		return itens;
	}

	public void setItens(List<Compra_Itens> itens) {
		this.itens = itens;
	}
}
