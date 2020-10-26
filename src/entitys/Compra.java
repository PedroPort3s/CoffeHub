package entitys;

import java.util.Date;
import java.util.List;

import control.compra_venda.ControlCompraItens;

public class Compra {

	private int cod;

	private Date data_origem;

	private Date data_recebido;

	private String status;

	private Fornecedor fornecedor;

	private Funcionario funcionario;

	private List<Compra_Item> itens;

	public double TotalCompra(){		
		double retorno = 0;
			
		if(this.itens != null && this.itens.size() > 0) {
			for(Compra_Item i : itens) {
				retorno += i.getValor_unitario();
			}
		}
		return retorno;
		
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

	public List<Compra_Item> getItens() {
		return itens;
	}

	public void setItens(List<Compra_Item> itens) {
		this.itens = itens;
	}
	
	// Método de validar com codigo
		public static void ValidarCompraCod(Compra compra) throws Exception{
			if (compra.getCod() <= 0 )
				throw new Exception("Informe o código da compra");
			if(compra.getData_origem() == null)
				throw new Exception("Data de origem invalida");
			if(compra.getStatus().equals(""))
				throw new Exception("Status inválido");
			if(compra.getFornecedor() != null)
			{
				if(compra.getFornecedor().getCod() <= 0)
					throw new Exception("Fornecedor inválido.");
			}
			if(compra.getFuncionario() != null)
			{
				if(compra.getFuncionario().getCod() <= 0)
					throw new Exception("Funcionario inválido.");
			}
			if(compra.getItens() != null && compra.getItens().size() > 0)
				Compra_Item.ValidarCompraItens(compra.getItens());		
		}
		
		// Método de validar sem codigo
		public static void ValidarCompraGravar(Compra compra) throws Exception{
			if(compra.getData_origem() == null)
				throw new Exception("Informe uma data de origem valida");
			if(compra.getStatus().equals(""))
				throw new Exception("Informe um status valido");
			if(compra.getFornecedor() != null)
			{
				if(compra.getFornecedor().getCod() <= 0)
					throw new Exception("Informe um fornecedor valido.");
			}
			if(compra.getFuncionario() != null)
			{
				if(compra.getFuncionario().getCod() <= 0)
					throw new Exception("Informe um funcionario valido.");
			}
			/*
			 * if(compra.getItens() == null || compra.getItens().size() == 0) throw new
			 * Exception("Informe pelo menos 1 item para prosseguir com esta compra");
			 */
		}

	@Override
	public String toString() {
		return "Compra: " + cod + " - data: " + data_origem + " - recebimento: " + data_recebido + " - status: "
				+ status + " - fornecedor: " + fornecedor + " - funcionario:" + funcionario;
	}
	
	
}
