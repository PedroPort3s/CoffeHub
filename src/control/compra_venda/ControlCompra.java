package control.compra_venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import Helper.db;
import dao.compra.CompraDAO;
import dao.compra.Compra_ItemDAO;
import entitys.Compra;
import utils.ConexaoMySql;

public class ControlCompra {
	
	private Connection conexao = null;
	
	public int Inserir(Compra compra) throws Exception {
		
		int retorno = 0;
		
		try {
			
			Compra.ValidarCompraGravar(compra);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			retorno = compraDAO.Inserir(compra);
			
			if(retorno <= 0) throw new Exception("Erro ao gravar a compra.");
			
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
	
public int Finalizar(Compra compra) throws Exception {
		
		int retorno = 0;
	
		try  {
			
			Compra.ValidarCompraCod(compra);
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			if(!compraDAO.Carregar(compra.getCod()).getStatus().equals("A")) 
				throw new Exception("Não é possível finalizar uma compra que não está aberta.");
			
			retorno = compraDAO.Finalizar(compra);
				
			if(retorno != 1) throw new Exception("Erro ao finalizar a compra.");
				
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
	
	public int Deletar(Compra compra) throws Exception {
		
		int retorno = 0;
	
		try  {
			
			Compra.ValidarCompraCod(compra);
			
			if(!compra.getStatus().equals("A")) {
				throw new Exception("Não é possível excluir uma venda que não está aberta.");
			}
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			retorno = new Compra_ItemDAO(conexao).RemoverItens(compra.getCod());
			
			if(retorno == compra.getItens().size()) {
				
				retorno = compraDAO.Deletar(compra.getCod());
				
				if(retorno != 1) throw new Exception("Erro ao deletar a compra.");
				
			}
			else {
				throw new Exception("Erro ao deletar os itens. Não foi possível excluir a compra");
			}
			
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
			
			Compra.ValidarCompraCod(compra);
			
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
			
			if(id <= 0) throw new Exception("Informe uma ID válida para carregar uma compra.");
			
			conexao = ConexaoMySql.getInstance().getConnection();
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			compra = compraDAO.Carregar(id);
			
			if(compra != null) {
				compra.setItens(new Compra_ItemDAO(conexao).CarregarItens(compra.getCod()));
			}
			
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
				throw new Exception("Informe se a pesquisa deve ser por funcionário ou fornecedor.");
			}
			
			if(codigo<=0) throw new Exception("Informe um código de " + tipoPesquisa.toLowerCase() +" válido para a pesquisa.");
			
			lstCompra = compraDAO.Buscar(dataIni, dataFim, codigo, tipoPesquisa);
			
			if(lstCompra == null || lstCompra.size() <= 0) throw new Exception("Não foi encontrado nenhum registro com os parâmetros informados.");
			
			conexao.close();
		}
		catch(SQLException ex){
			throw ex;
		} catch (Exception e) {
			throw e;
		}
		
		return lstCompra;
	}
	
	public double TotalVendasDia(Date data) throws Exception {
		double retorno = 0;
		try {
			
			conexao = ConexaoMySql.getInstance().getConnection();
			
			CompraDAO compraDAO = new CompraDAO(conexao);
			
			if(data == null) throw new Exception("Informe uma data para obter o total de compras naquele dia.");
			
			retorno = compraDAO.TotalVendasDia(data);
			
			conexao.close();
		}
		catch(SQLException ex){
			throw ex;
		} catch (Exception e) {
			throw e;
		}
		
		return retorno;
	}
	
	
}
