package entitys;

import java.util.Date;
import java.util.List;

public class Venda {

	private int cod;

	private Date data_origem;

	private Date data_confirmacao;

	private String status;
	
	private Funcionario funcionario;

	private Cliente cliente;

	private List<Venda_Item> itens;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public List<Venda_Item> getItens() {
		return itens;
	}

	public void setItens(List<Venda_Item> itens) {
		this.itens = itens;
	}	
	
	// Método de validar com codigo
			public static void ValidarVendaCod(Venda venda) throws Exception{
				if (venda.getCod() <= 0 )
					throw new Exception("Informe o código da venda");
				if(venda.getData_origem() == null)
					throw new Exception("Informe uma data de origem valida");
				if(venda.getStatus().equals(""))
					throw new Exception("Informe um status valido");
				if(venda.getCliente() != null)
				{
					if(venda.getCliente().getCod() <= 0)
						throw new Exception("Informe um cliente valido.");
				}
				if(venda.getFuncionario() != null)
				{
					if(venda.getFuncionario().getCod() <= 0)
						throw new Exception("Informe um funcionario valido.");
				}
				if(venda.getItens().size() == 0)
					throw new Exception("Esta venda não possui itens");
				else {
					Venda_Item.ValidarVendaItens(venda.getItens());
				}
			}
			
			// Método de validar sem codigo
			public static void ValidarVendaGravar(Venda venda) throws Exception{
				if(venda.getData_origem() == null)
					throw new Exception("Informe uma data de origem valida");
				if(venda.getStatus().equals(""))
					throw new Exception("Informe um status valido");
				if(venda.getCliente() != null)
				{
					if(venda.getCliente().getCod() <= 0)
						throw new Exception("Informe um cliente valido.");
				}
				if(venda.getFuncionario() != null)
				{
					if(venda.getFuncionario().getCod() <= 0)
						throw new Exception("Informe um funcionario valido.");
				}
				if(venda.getItens().size() == 0)
					throw new Exception("Informe pelo menos 1 item para prosseguir com esta venda.");
			}

	public double TotalVenda(){
		double retorno = 0;
		if(this.itens != null && this.itens.size() > 0) {
			for(Venda_Item i : itens) {
				retorno += i.getValor_venda();
			}
		}
		return retorno;
	}
}
