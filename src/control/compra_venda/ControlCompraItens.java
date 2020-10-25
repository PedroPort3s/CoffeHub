package control.compra_venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.compra.Compra_ItemDAO;
import entitys.Compra;
import entitys.Compra_Item;
import utils.ConexaoMySql;

public class ControlCompraItens {
	
	private Connection conexao = null;
	
	public ControlCompraItens(Connection conn) {
		this.conexao = conn;
	}	
	
	
	public ControlCompraItens() {
		super();
	}
	
	public int AdicionarItem(Compra compra, Compra_Item item) throws Exception {
		
		int retorno = 0;
		
		try {
			
			Compra.ValidarCompraCod(compra);
			Compra_Item.ValidarCompraItem(item);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			Compra_ItemDAO compraItemDAO = new Compra_ItemDAO(conexao);
			
			retorno = compraItemDAO.AdicionarItem(compra.getCod(), item);
			
			if(retorno != 1) throw new Exception("Erro ao gravar o item: " + item.getProduto().getCod() + " - " + item.getProduto().getDescricao() + " na compra.");
			
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
	
	public int RemoverItem(Compra compra, Compra_Item item) throws Exception {
		
		int retorno = 0;
		
		try {
			
			Compra.ValidarCompraCod(compra);
			Compra_Item.ValidarCompraItem(item);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			Compra_ItemDAO compraItemDAO = new Compra_ItemDAO(conexao);
			
			retorno = compraItemDAO.RemoverItem(compra.getCod(), item);
			
			if(retorno != 1) throw new Exception("Erro ao remover o item: " + item.getProduto().getCod() + " - " + item.getProduto().getDescricao() + " na compra.");
			
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
	
	public int AlterarQuantidadeItem(Compra compra, Compra_Item item) throws Exception {
		
		int retorno = 0;
		
		try {
			
			Compra.ValidarCompraCod(compra);
			Compra_Item.ValidarCompraItem(item);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			Compra_ItemDAO compraItemDAO = new Compra_ItemDAO(conexao);
			
			retorno = compraItemDAO.AlterarQtd(compra.getCod(), item);
			
			if(retorno != 1) throw new Exception("Erro ao alterar a quantidade do item: " + item.getProduto().getCod() + " - " + item.getProduto().getDescricao() + " na compra.");
			
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
	
	public int AlterarValorUnitarioItem(Compra compra, Compra_Item item) throws Exception {
		
		int retorno = 0;
		
		try {
			
			Compra.ValidarCompraCod(compra);
			Compra_Item.ValidarCompraItem(item);
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			
			Compra_ItemDAO compraItemDAO = new Compra_ItemDAO(conexao);
			
			retorno = compraItemDAO.AlterarValor(compra.getCod(), item);
			
			if(retorno != 1) throw new Exception("Erro ao alterar o valor unitário do item: " + item.getProduto().getCod() + " - " + item.getProduto().getDescricao() + " na compra.");
			
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
	
	public List<Compra_Item> CarregarItensDaCompra(Compra compra) throws Exception{
		List<Compra_Item> lstCompraItem = null;
		try {		
			
			conexao = ConexaoMySql.getInstance().getConnection();
			
			Compra_ItemDAO compraItemDAO = new Compra_ItemDAO(conexao);
			
			Compra.ValidarCompraCod(compra);
			
			lstCompraItem = compraItemDAO.CarregarItens(compra.getCod());
			
			if(lstCompraItem == null || lstCompraItem.size() <= 0) throw new Exception("Não foi encontrado nenhum registro com os parâmetros informados.");
			
			conexao.close();
		}
		catch(SQLException ex){
			throw ex;
		} catch (Exception e) {
			throw e;
		}
		
		return lstCompraItem;
	}
	
	
}
