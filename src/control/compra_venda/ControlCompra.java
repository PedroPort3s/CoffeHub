package control.compra_venda;

import entitys.Compra;


public class ControlCompra {
	
	// M�todo de validar com num_item
	private void ValidarCompraNumItem(Compra compra) throws Exception{
		if (compra.getCod() <= 0 )
			throw new Exception("Informe o c�digo da compra");
		if(compra.getData_origem() == null)
			throw new Exception("Data de origem invalida");
		if(compra.getStatus().equals(""))
			throw new Exception("Status inv�lido");
		if(compra.getFornecedor() != null)
		{
			if(compra.getFornecedor().getCod() <= 0)
				throw new Exception("Fornecedor inv�lido.");
		}
		if(compra.getFuncionario() != null)
		{
			if(compra.getFuncionario().getCod() <= 0)
				throw new Exception("Funcionario inv�lido.");
		}
		if(compra.getItens().size() == 0)
			throw new Exception("Esta compra n�o possui itens");
		else {
			new ControlCompraItens().ValidarCompraItens(compra.getItens());
		}
	}
	
	// M�todo de validar sem num_item
	private void ValidarCompraGravar(Compra compra) throws Exception{
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
		if(compra.getItens().size() == 0)
			throw new Exception("Informe pelo menos 1 item para prosseguir com esta compra");
	}
}
