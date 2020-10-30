package dao.compra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.FornecedorDAO;
import dao.interfaces.ICompraVenda;
import entitys.Compra;


public class CompraDAO implements ICompraVenda<Compra>{
	
	private Connection conexao = null;
	
	public CompraDAO(Connection conn) {
		conexao = conn;
	}
	
	private String Select_Compra() {
		StringBuilder sql = new StringBuilder();
		sql.append("select c.cod, c.data_origem, c.data_recebido, c.valor_total, c.status, c.cod_Fornecedor, c.cod_Funcionario from compra as c");
		return sql.toString();
	}
	

	@Override
	public int Inserir(Compra compra) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;
		
		try {
			String sql = "INSERT INTO compra(cod, data_origem, data_recebido, valor_total, status, cod_Fornecedor, cod_Funcionario) VALUES (?,?,?,?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			retorno = this.ProximoCodCompra();
			
			statement.setInt(1, retorno);
			statement.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			statement.setString(3, null);
			statement.setDouble(4, 0);
			statement.setString(5, "A");
			statement.setInt(6, compra.getFornecedor().getCod());
			statement.setInt(7, compra.getFuncionario().getCod());
			
			if(statement.executeUpdate() != 1) {
				throw new Exception("Não foi possivel gravar a compra");
			}
			
		} catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		
		return retorno;
	}

	@Override
	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		int retorno = 0;
		try {
			
			String sql = "DELETE FROM compra WHERE cod= " + id;

			PreparedStatement statement = conexao.prepareStatement(sql);
			
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
	public int Editar(Compra compra) throws SQLException {
		int retorno = 0;
		try {			
			
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE compra SET");
			sql.append(" valor_total = " + compra.TotalCompra() + ",");
			sql.append(" cod_Fornecedor = "+compra.getFornecedor().getCod()+",");
			sql.append(" cod_Funcionario = "+ compra.getFuncionario().getCod());
			sql.append(" WHERE cod = "+ compra.getCod());

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
	public Compra Carregar(int id) throws Exception {
		Compra compra = null;
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_Compra());
			sql.append(" where c.cod ="+id);			

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				compra = this.PreencherCompra(resultSet);
			}
			
			statement.close();
			
		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		return compra;
	}

	public List<Compra> Buscar(String status) throws Exception {
		List<Compra> lista = new ArrayList<Compra>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select_Compra());
			sql.append(" where c.status='"+ status +"'");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Compra compra = this.PreencherCompra(resultSet);
				lista.add(compra);
			}
			
			statement.close();
			
		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
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
	public List<Compra> Buscar() throws Exception {
		List<Compra> lista = new ArrayList<Compra>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select_Compra());

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Compra compra = this.PreencherCompra(resultSet);
				lista.add(compra);
			}
			
			statement.close();
			
		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}
	
	public List<Compra> Buscar(Date DataInicio, Date DataFim, String status) throws Exception {
		List<Compra> lista = new ArrayList<Compra>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select_Compra());
			sql.append(" where c.status='"+ status +"'");
			sql.append(" and c.data_origem <= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataFim)+"'");
			sql.append(" and c.data_origem >= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataInicio)+"'");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Compra compra = this.PreencherCompra(resultSet);
				lista.add(compra);
			}
			
			statement.close();
			
		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}
	
	public List<Compra> Buscar(Date DataInicio, Date DataFim) throws Exception {
		List<Compra> lista = new ArrayList<Compra>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select_Compra());
			
			sql.append(" where c.data_origem <= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataFim)+"'");
			sql.append(" and c.data_origem >= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataInicio)+"'");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Compra compra = this.PreencherCompra(resultSet);
				lista.add(compra);
			}
			
			statement.close();
			
		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}
	
	public List<Compra> Buscar(Date DataInicio, Date DataFim, int codigo, String tipoPesquisa) throws Exception {
		List<Compra> lista = new ArrayList<Compra>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select_Compra());
			
			if(tipoPesquisa.toUpperCase().equals("FUNCIONARIO")) {
				sql.append(" where c.cod_Funcionario = "+codigo);
			}
			else if(tipoPesquisa.toUpperCase().equals("FORNECEDOR")) {
				sql.append(" where c.cod_Fornecedor = "+codigo);
			}
			
			sql.append(" and c.data_origem <= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataFim)+"'");
			sql.append(" and c.data_origem >= '"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataInicio)+"'");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Compra compra = this.PreencherCompra(resultSet);
				lista.add(compra);
			}
			
			statement.close();
			
		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}
	
	public double TotalVendasDia(Date data) throws SQLException {
		
		double retorno = 0;
		
		try {
			String sum = "select ifnull(sum(valor_total), 0) as 'totalVendas' from compra where data_recebido='"+ new SimpleDateFormat("yyyy-MM-dd").format(data) +"' and status='F' ";
			PreparedStatement statement = conexao.prepareStatement(sum);
			ResultSet resultSet;
			resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				retorno = resultSet.getDouble("totalVendas");
			}
			
			if (retorno < 0) throw new Error("Não foi possível recuperar o total das compras no dia "+ data.toString());
			
			statement.close();
		} catch (SQLException e) {
			throw e;
		}		
		
		return retorno;
	}
	
	public Compra PreencherCompra(ResultSet resultSet) throws Exception {
		Compra compra = new Compra();
		//c.valor_total, , c.cod_Fornecedor, c.cod_Funcionario
		
		compra.setCod(resultSet.getInt("c.cod"));
		compra.setData_origem(resultSet.getDate("c.data_origem"));
		compra.setData_recebido(resultSet.getDate("c.data_recebido"));
		compra.setStatus(resultSet.getString("c.status"));
		compra.setItens(new Compra_ItemDAO(conexao).CarregarItens(compra.getCod()));
		compra.setFornecedor(new FornecedorDAO().buscarId(resultSet.getInt("c.cod_Fornecedor")));
		/*
		 * compra.setFuncionario(new
		 * FuncionarioDAO().buscarId(resultSet.getInt("c.cod_Funcionario"))); carregar
		 * fornecedor c.cod_Fornecedor, c.cod_Funcionario
		 */
		return compra;
	}
	
	private int ProximoCodCompra() throws SQLException{
		int retorno = 0;
		
		try {
			String max = "select ifnull(max(cod),0) as 'maior' from compra";
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
	public int Finalizar(Compra compra) throws Exception {
		
			int retorno = 0;
			try {			
				
				StringBuilder sql = new StringBuilder();
				sql.append("UPDATE compra SET");
				sql.append(" valor_total = " + compra.TotalCompra() + ",");
				sql.append(" data_recebido = '"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +"',");
				sql.append(" status = 'F'");
				sql.append(" WHERE cod = "+ compra.getCod());

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
