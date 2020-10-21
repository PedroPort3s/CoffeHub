package dao.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.interfaces.ICompraVenda;
import entitys.Compra_Itens;

public class Compra_ItensDAO implements ICompraVenda<Compra_Itens>{
	
	private Connection conexao = null;
	
	public Compra_ItensDAO(Connection conn) {
		this.conexao = conn;
	}

	@Override
	public String Select() {
		StringBuilder sql = new StringBuilder();
		sql.append("select num_item, cod_Compra, cod_Produto, qtdVenda, valor_venda from compraproduto");
		return sql.toString();
	}

	@Override
	public int AdicionarItem(int cod, Compra_Itens item) throws SQLException {
		int retorno = 0;
		
		try {
			String sql = "INSERT INTO compraproduto(num_item,cod_Compra, cod_Produto, qtdVenda, valor_venda) VALUES (?,?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			
			statement.setInt(1, this.ProximoSequencial(cod));
			statement.setInt(2, cod);
			statement.setInt(3, item.getProduto().getCod());
			statement.setDouble(4, item.getQtd_item());
			statement.setDouble(5, item.getValor_unitario());
			
			retorno = statement.executeUpdate();
			
		} catch (SQLException sqlEx) {
			throw sqlEx;
		} catch (Exception ex) {
			throw ex;
		}
		
		return retorno;
	}

	@Override
	public int RemoverItem(int cod, Compra_Itens item) throws SQLException {
		
		int retorno = 0;
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM compraproduto WHERE cod_compra= " + cod);
			sql.append(" and cod_Produto= "+ item.getProduto().getCod());
			sql.append(" and num_item=" + item.getNum_item());
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			retorno = statement.executeUpdate();
			
			statement.close();
			
		}  catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return retorno;
	}

	@Override
	public int AlterarQtd(int cod, Compra_Itens item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int AlterarValor(int cod, Compra_Itens item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double TotalItens(int cod) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ProximoSequencial(int cod) {
		// TODO Auto-generated method stub
		return 0;
	}
}
