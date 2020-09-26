package entitys;

import java.util.Date;
import java.util.List;

public class Venda {

	private int cod;

	private Date data_origem;

	private Date data_confirmacao;

	private int status;
	
	private Funcionario funcionario;

	private Cliente cliente;

	private List<Venda_Itens> itens;

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

	public Date getData_confirmacao() {
		return data_confirmacao;
	}

	public void setData_confirmacao(Date data_confirmacao) {
		this.data_confirmacao = data_confirmacao;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Venda_Itens> getItens() {
		return itens;
	}

	public void setItens(List<Venda_Itens> itens) {
		this.itens = itens;
	}	

	public double TotalVenda(){
		return 0;
	}
}
