package control.compra_venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import Helper.db;
import dao.ProdutoDAO;
import dao.compra.CompraDAO;
import entitys.Compra;
import entitys.Produto;
import utils.ConexaoMySql;


public class ControlCompra {
	
	private Connection conexao = null;
	
	public int Inserir(Compra compra) throws Exception {
		
		int retorno = 0;
		
		try {
			
			this.ValidarCompraGravar(compra);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			retorno = compraDAO.Inserir(compra);
			
			if(retorno != 1) throw new Exception("Erro ao gravar a compra.");
			
			conexao.commit();
			conexao.close();
		}
		catch(SQLException ex){
			if(conexao != null) conexao.rollback();
			throw ex;
		} catch(Exception e) {
			if(conexao != null) conexao.rollback();
			throw e;
		}
		
		
		return retorno;
	}
	
	public int Deletar(Compra compra) throws Exception {
		
		int retorno = 0;
	
		try  {
			
			this.ValidarCompraCod(compra);
			
			if(!compra.getStatus().equals("A")) {
				throw new Exception("N�o � poss�vel excluir uma venda que n�o est� aberta.");
			}
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			// primeiro deletar os itens
			retorno = compraDAO.Deletar(compra.getCod());
			
			if(retorno != 1) throw new Exception("Erro ao deletar a compra.");
			
			conexao.commit();
			conexao.close();
		}
		catch(SQLException ex){
			if(conexao != null) conexao.rollback();
			
			throw ex;
		}
		catch(Exception e) {
			if(conexao != null) conexao.rollback();
			throw e;
		}
		
		return retorno;
	}
	
	
	public int Editar(Compra compra) throws Exception {
		int retorno = 0;
		try {
			
			this.ValidarCompraCod(compra);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			retorno = compraDAO.Editar(compra);
			
			if(retorno != 1) throw new Exception("Erro ao editar a compra.");
			
			conexao.commit();
			conexao.close();
		}
		catch(SQLException ex){
			if(conexao != null) conexao.rollback();
			
			throw ex;
		}
		catch(Exception e) {
			if(conexao != null) conexao.rollback();
			throw e;
		}
		
		
		return retorno;
	}
	
	public Compra Carregar(int id) throws Exception {
		
		Compra compra = null;
		
		try {
			
			if(id <= 0) throw new Exception("Informe uma ID v�lida para carregar uma compra.");
			
			conexao = ConexaoMySql.getInstance().getConnection();
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			compra = compraDAO.Carregar(id);
			
			conexao.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return compra;
		
	}
	
	public List<Compra> Listar(Date dataIni, Date dataFim,String statusCompra) throws Exception {
		List<Compra> lstCompra = null;
		try {		
			
			conexao = ConexaoMySql.getInstance().getConnection();
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			db.VerificarPeriodo(dataIni, dataFim);
			
			if(statusCompra.trim().equals("")) 
			{
				lstCompra = compraDAO.Buscar(dataIni, dataFim);
			}
			else
			{
				lstCompra = compraDAO.Buscar(dataIni, dataFim, statusCompra);
			}
			
			conexao.close();
		}
		catch(SQLException ex){
			throw ex;
		} catch (Exception e) {
			throw e;
		}
		
		return lstCompra;
	}
	
	public List<Compra> ListarPorPessoa(Date dataIni, Date dataFim, int codigo, String tipoPesquisa) throws Exception {
		List<Compra> lstCompra = null;
		try {		
			
			conexao = ConexaoMySql.getInstance().getConnection();
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			db.VerificarPeriodo(dataIni, dataFim);
			
			if(!tipoPesquisa.toUpperCase().equals("FUNCIONARIO") || !tipoPesquisa.toUpperCase().equals("FORNECEDOR")){
				throw new Exception("Informe se a pesquisa deve ser por funcion�rio ou fornecedor.");
			}
			
			if(codigo<=0) throw new Exception("Informe um c�digo de " + tipoPesquisa.toLowerCase() +" v�lido para a pesquisa.");
			
			lstCompra = compraDAO.Buscar(dataIni, dataFim, codigo, tipoPesquisa);
			
			if(lstCompra == null || lstCompra.size() <= 0) throw new Exception("N�o foi encontrado nenhum registro com os par�metros informados.");
			
			conexao.close();
		}
		catch(SQLException ex){
			throw ex;
		} catch (Exception e) {
			throw e;
		}
		
		return lstCompra;
	}
	
	// M�todo de validar com num_item
	private void ValidarCompraCod(Compra compra) throws Exception{
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
