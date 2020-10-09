package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.interfaces.IPadraoDB;
import entitys.Compra;
import entitys.Produto;

public class CompraDAO implements IPadraoDB<Compra>{
	
	private Connection conexao = null;
	
	public CompraDAO(Connection conn) {
		conexao = conn;
	}
	
	private String Select_Compra() {
		StringBuilder sql = new StringBuilder();
		sql.append("select c.cod, c.data_origem, c.date_recebido, c.valor_total, c.status, c.cod_Fornecedor, c.cod_Funcionario from compra as c");
		return sql.toString();
	}
	

	@Override
	public int Inserir(Compra obj) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;
		
		try {
			String sql = "INSERT INTO compra(cod, data_origem, date_recebido, valor_total, status, cod_Fornecedor, cod_Funcionario) VALUES (?,?,?,?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			
			Produto prod = null;
			
			statement.setInt(1, this.ProximoCodCompra());
			statement.setString(2, prod.getDescricao());
			statement.setDouble(3, prod.getValor_un());
			statement.setInt(4, prod.getQtd_atual());
			statement.setInt(5, prod.getCategoria().getCod());
			statement.setString(6, prod.getUnidadeMedida());
			
			retorno = statement.executeUpdate();
			
			
		} catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		
		return retorno;
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
	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Editar(Compra obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Compra Carregar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compra> Buscar(String pesquisa) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Compra> Buscar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
