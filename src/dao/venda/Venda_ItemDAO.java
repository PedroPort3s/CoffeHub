package dao.venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Helper.db;
import dao.ProdutoDAO;
import dao.interfaces.ICompraVendaItem;
import entitys.Venda_Item;

public class Venda_ItemDAO implements ICompraVendaItem<Venda_Item> {
	
	private Connection conexao = null;
	
	/*
	 * num_item, cod_Venda, cod_Produto, qtdVenda, valor_venda
	 */
	
	public Venda_ItemDAO(Connection conn) {
		conexao = conn;
	}

	@Override
	public String Select() {
		StringBuilder sql = new StringBuilder();
		sql.append("select num_item, cod_Venda, cod_Produto, qtdVenda, valor_venda from vendaitem");
		return sql.toString();
	}

	@Override
	public int AdicionarItem(int cod, Venda_Item item) throws Exception {
		int retorno = 0;
		
		try {
			String sql = "INSERT INTO vendaitem(num_item, cod_Venda, cod_Produto, qtdVenda, valor_venda) VALUES (?,?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			
			statement.setInt(1, this.ProximoSequencial(cod));
			statement.setInt(2, cod);
			statement.setInt(3, item.getProduto().getCod());
			statement.setDouble(4, item.getQtd_item());
			statement.setDouble(5, item.getValor_venda());
			
			retorno = statement.executeUpdate();
			
		} catch (SQLException sqlEx) {
			throw sqlEx;
		} catch (Exception ex) {
			throw ex;
		}
		
		return retorno;
	}

	@Override
	public int RemoverItem(int cod, Venda_Item item) throws SQLException {
		int retorno = 0;
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM vendaitem WHERE cod_Venda= " + cod);
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
	public int RemoverItens(int cod) throws SQLException {
		
		int retorno = 0;
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM vendaitem WHERE cod_Venda= " + cod);
			
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
	public int AlterarValor(int cod, Venda_Item item) throws SQLException {
		int retorno = 0;
		try {			
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE vendaitem SET");
			sql.append(" valor_venda=" + item.getValor_venda());
			sql.append(" WHERE cod_Venda= " + cod);
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
	public double TotalItens(int cod) throws SQLException {
double retorno = 0;
		
		try {
			String soma = "select sum(valor_venda) * qtdVenda as 'TotalItens' from vendaitem where cod_Venda =" + cod;
			PreparedStatement statement = conexao.prepareStatement(soma);
			ResultSet resultSet;
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				retorno = resultSet.getInt("TotalItens");
			}
			
			if (retorno < 0) throw new Error("Não foi possível recuperar o total dos itens na venda "+ cod);
			
			statement.close();
		} catch (SQLException e) {
			throw e;
		}		
		
		return retorno;
	}

	@Override
	public int ProximoSequencial(int cod) throws Exception {
		return db.ProximaID(conexao, "num_item", "vendaitem");
	}

	@Override
	public List<Venda_Item> CarregarItens(int cod) throws Exception {
		List<Venda_Item> lista = new ArrayList<Venda_Item>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select());
			sql.append(" where cod_Venda = "+ cod);

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Venda_Item vendaItem = this.PreencherItem(resultSet);
				lista.add(vendaItem);
			}
			
			statement.close();
			
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}

	@Override
	public Venda_Item PreencherItem(ResultSet rs) throws Exception {
		Venda_Item item = new Venda_Item();
		item.setNum_item(rs.getInt("num_item"));
		item.setProduto(new ProdutoDAO(conexao).Carregar(rs.getInt("cod_Produto")));
		item.setQtd_item(rs.getDouble("qtdVenda"));
		item.setValor_venda(rs.getDouble("valor_venda"));
		return item;
	}

	@Override
	public int AlterarQtd(int cod, Venda_Item item) throws SQLException {
		int retorno = 0;
		try {			
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE vendaitem SET");
			sql.append(" qtdVenda = " + item.getQtd_item());
			sql.append(" WHERE cod_Venda= " + cod);
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

	
}
