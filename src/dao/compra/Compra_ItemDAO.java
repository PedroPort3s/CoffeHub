package dao.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ProdutoDAO;
import dao.interfaces.ICompraVendaItem;
import entitys.Compra_Item;


public class Compra_ItemDAO implements ICompraVendaItem<Compra_Item>{
	
	private Connection conexao = null;
	
	public Compra_ItemDAO(Connection conn) {
		this.conexao = conn;
	}

	@Override
	public String Select() {
		StringBuilder sql = new StringBuilder();
		sql.append("select num_item, cod_Compra, cod_Produto, qtdVenda, valor_venda from compraproduto");
		return sql.toString();
	}

	@Override
	public int AdicionarItem(int cod, Compra_Item item) throws SQLException {
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
	public int RemoverItem(int cod, Compra_Item item) throws SQLException {
		
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
	public int RemoverItens(int cod) throws SQLException {
		
		int retorno = 0;
		
		try {
			
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM compraproduto WHERE cod_compra= " + cod);
			
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
	public int AlterarQtd(int cod, Compra_Item item) throws SQLException {
		int retorno = 0;
		try {			
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE compraproduto SET");
			sql.append(" qtdVenda = " + item.getQtd_item());
			sql.append(" WHERE cod_compra= " + cod);
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
	public int AlterarValor(int cod, Compra_Item item) throws SQLException {
		int retorno = 0;
		try {			
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE compraproduto SET");
			sql.append(" valor_venda=" + item.getValor_unitario());
			sql.append(" WHERE cod_compra= " + cod);
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
			String soma = "select sum(valor_venda) * qtdVenda as 'TotalItens' from compraproduto where cod_Compra =" + cod;
			PreparedStatement statement = conexao.prepareStatement(soma);
			ResultSet resultSet;
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				retorno = resultSet.getInt("TotalItens");
			}
			
			if (retorno < 0) throw new Error("Não foi possível recuperar o total dos itens na compra "+ cod);
			
			statement.close();
		} catch (SQLException e) {
			throw e;
		}		
		
		return retorno;
	}

	@Override
	public int ProximoSequencial(int cod) throws SQLException {
	 int retorno = 0;
		
		try {
			String max = "select ifnull(max(num_item),0) as 'maior' from compraproduto where cod_Compra=" + cod;
			PreparedStatement statement = conexao.prepareStatement(max);
			ResultSet resultSet;
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				retorno = resultSet.getInt("maior");
			}
			
			if (retorno < 0) throw new Error("Não foi possível recuperar o proximo número dos produtos");
			
			statement.close();
		} catch (SQLException e) {
			throw e;
		}		
		
		return retorno + 1;
	}

	@Override
	public List<Compra_Item> CarregarItens(int cod) throws Exception {
		List<Compra_Item> lista = new ArrayList<Compra_Item>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select());
			sql.append(" where cod_Compra = "+ cod);

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Compra_Item compraItem = this.PreencherItem(resultSet);
				lista.add(compraItem);
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
	public Compra_Item PreencherItem(ResultSet rs) throws Exception {
		Compra_Item item = new Compra_Item();
		item.setNum_item(rs.getInt("num_item"));
		item.setProduto(new ProdutoDAO(conexao).Carregar(rs.getInt("cod_Produto")));
		item.setQtd_item(rs.getDouble("qtdVenda"));
		item.setValor_unitario(rs.getDouble("valor_venda"));
		return item;
	}
}
