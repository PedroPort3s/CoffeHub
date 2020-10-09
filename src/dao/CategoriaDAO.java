package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Helper.Verifica;
import dao.interfaces.ICategoriaDAO;
import entitys.Categoria;

public class CategoriaDAO implements ICategoriaDAO {
	
	private Connection conexao = null;
	public CategoriaDAO(Connection conn) {
		conexao = conn;
	}
	
	private String Select_Categoria() {
		StringBuilder sql = new StringBuilder();
		sql.append("select cod,nome from categoria");
		return sql.toString();
	}
	
	@Override
	public int Inserir(Categoria cat) throws Exception {
		
		int retorno = 0;
		
		try {

			String sql = "INSERT INTO categoria(cod,nome) VALUES (?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			
			statement.setInt(1, this.ProximoNumeroCategoria());
			statement.setString(2, cat.getNome());
			retorno = statement.executeUpdate();
			
		} catch (ClassNotFoundException classEx) {
			/* classEx.printStackTrace(); */
			throw classEx;
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
	public int Deletar(int id) throws SQLException, ClassNotFoundException {
		
		int retorno = 0;
		try {

			String sql = "DELETE FROM categoria WHERE cod= ?";

			PreparedStatement statement = conexao.prepareStatement(sql);

			statement.setInt(1, id);
			retorno = statement.executeUpdate();
			
			statement.close();
		} 
		catch (SQLException sqlEx)
		{ 
			/* int erro = sqlEx.getErrorCode(); */
			if(sqlEx.getErrorCode() == 1451)
			{
				throw new SQLException("Esta categoria esta sendo utilizada por um ou mais produtos, para deletar a mesma, favor deletar os produtos antes.");
			}
			else 
			{
			throw sqlEx;
			}
		}
		catch (Exception ex) 
		{
			/* ex.printStackTrace(); */
			throw ex;
		}
		return retorno;
	}

	@Override
	public int Editar(Categoria cat) throws SQLException, ClassNotFoundException {
		int retorno = 0;
		try {

			String sql = "UPDATE categoria SET nome = ? WHERE cod = ?";

			PreparedStatement statement = conexao.prepareStatement(sql);

			statement.setString(1, cat.getNome());
			statement.setInt(2, cat.getCod());

			retorno = statement.executeUpdate();
			statement.close();

		}  catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		return retorno;
	}

	@Override
	public Categoria Carregar(int id) throws SQLException, ClassNotFoundException {
		Categoria categoria = null;
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_Categoria());
			sql.append(" where cod="+id);			

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				categoria = new Categoria(resultSet.getInt("cod"), resultSet.getString("nome"));	
			}
			
			statement.close();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return categoria;
	}

	@Override
	public List<Categoria> Buscar(String pesquisa) throws ClassNotFoundException, SQLException {
		List<Categoria> lista = new ArrayList<Categoria>();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_Categoria());
			
			if(Verifica.ehNumeroInt(pesquisa)){
				sql.append(" where cod="+pesquisa);
			}
			else{	
				sql.append(" where nome like ('%"+pesquisa+"%')");
			}

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Categoria categoria = new Categoria(resultSet.getInt("cod"), resultSet.getString("nome"));
				lista.add(categoria);
			}
			
			statement.close();
		}  catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		return lista;
	}

	@Override
	public List<Categoria> Buscar() throws ClassNotFoundException, SQLException {
		List<Categoria> lista = new ArrayList<Categoria>();

		try {	

			PreparedStatement statement = conexao.prepareStatement(Select_Categoria());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				/*
				 * lista.add(new Categoria(resultSet.getInt("cod"),
				 * resultSet.getString("nome")));
				 */
				
				Categoria categoria = new Categoria();
				categoria.setCod(resultSet.getInt("cod"));
				categoria.setNome(resultSet.getString("nome"));
				
				lista.add(categoria);
			}
			
			statement.close();
			
			
		}catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}

		return lista;
	}
	
	private int ProximoNumeroCategoria() throws Exception {
		
		
		int numeroCategoria = 0;

		try {		

			PreparedStatement statement = conexao.prepareStatement("select ifnull(max(cod),0) as 'maior' from categoria");

			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				numeroCategoria = resultSet.getInt("maior");
			}
			
			if (numeroCategoria < 0) throw new Exception ("Não foi possível recuperar o proximo número da categoria");
			
			statement.close();
			
			
		} catch (ClassNotFoundException classEx) {
			/* classEx.printStackTrace(); */
			throw classEx;
		} catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}

		return numeroCategoria + 1;
	}

}
