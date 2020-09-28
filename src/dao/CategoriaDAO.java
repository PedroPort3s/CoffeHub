package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.ICategoriaDAO;
import entitys.Categoria;
import entitys.Pessoa;
import utils.ConexaoMySql;

public class CategoriaDAO implements ICategoriaDAO {

	private String nomeTable = "tb_categoria";
	private String pk = "cod_categoria";
	private String col1 = "nome_categoria";

	
	@Override
	public void inserir(Categoria obj) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "INSERT INTO "+nomeTable+"("+col1+") VALUES (?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, obj.getNome());
			statement.execute();
			connection.close();
		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
			System.out.println(
					"esse erro vai acontecer por conta do connector, pesquisem sobre" + " ----  erro no inserir");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("esse erro foi estritamente no DriverManeger, " + "deem uma olhada, criar o banco talvez"
					+ "---erro no inserir");
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Fudeo marreco" + "---erro no inserir");
		}
	}

	@Override
	public void deletar(int id) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "DELETE FROM "+nomeTable+" WHERE "+pk+" = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, id);
			statement.execute();
			statement.close();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
			System.out
					.println("esse erro vai acontecer por conta do connector, pesquisem sobre" + "---erro no deletar");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("esse erro foi estritamente no DriverManeger, " + "deem uma olhada, criar o banco talvez"
					+ "---erro no deletar");
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Fudeo marreco" + "---erro no deletar");
		}
	}

	@Override
	public void editar(Categoria obj) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "UPDATE "+nomeTable+" SET "+col1+" = ? WHERE "+pk+" = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, obj.getNome());
			statement.setInt(2, obj.getCod());

			statement.execute();
			statement.close();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
			System.out.println("esse erro vai acontecer por conta do connector, pesquisem sobre" + "---erro no editar");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("esse erro foi estritamente no DriverManeger, " + "deem uma olhada, criar o banco talvez"
					+ "---erro no editar");
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Fudeo marreco" + "---erro no editar");
		}
	}

	@Override
	public List<Categoria> buscarId(int id) {
		List<Categoria> lista = new ArrayList<Categoria>();
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "SELECT * FROM "+nomeTable+" WHERE "+pk+" = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Categoria categoria = new Categoria(resultSet.getInt(pk), resultSet.getString(col1));
				lista.add(categoria);
			}
			
			statement.close();
			connection.close();
		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
			System.out.println("esse erro vai acontecer por conta do connector, pesquisem sobre" + "---erro no buscar");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("esse erro foi estritamente no DriverManeger, " + "deem uma olhada, criar o banco talvez"
					+ "---erro no buscar");
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Fudeo marreco" + "---erro no buscar");
		}
		return lista;
	}

	@Override
	public List<Categoria> listar() {
		List<Categoria> lista = new ArrayList<Categoria>();

		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "SELECT * FROM "+nomeTable;

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Categoria categoria = new Categoria(resultSet.getInt(pk), resultSet.getString(col1));
				lista.add(categoria);
			}
			statement.close();
			connection.close();
		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
			System.out.println("esse erro vai acontecer por conta do connector, pesquisem sobre" + "---erro no listar");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println("esse erro foi estritamente no DriverManeger, " + "deem uma olhada, criar o banco talvez"
					+ "---erro no listar");
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Fudeo marreco" + "---erro no listar");
		}

		return lista;
	}

}
