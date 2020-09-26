package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IProdutoDAO;
import entitys.Categoria;
import entitys.Pessoa;
import entitys.Produto;
import utils.ConexaoMySql;

public class ProdutoDAO implements IProdutoDAO {

	private String nomeTable = "tb_produto";
	private String pk = "cod_produto";
	private String col1 = "nome_produto";
	private String col2 = "valor_un";
	private String col3 = "tb_categoria_cod_categoria";
	private String nomeTablecategoria = "tb_categoria";
	private String tc_col1 = "cod_categoria";
	private String tc_col2 = "nome_categoria";

	@Override
	public void inserir(Produto obj) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "INSERT INTO " + nomeTable + "(" + col1 + "," + col2 + "," + col3 + ") VALUES (?, ?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, obj.getNome_produto());
			statement.setDouble(2, obj.getValor());
			statement.setInt(3, obj.getCategoria().getCod_categoria());
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

			String sql = "DELETE FROM " + nomeTable + " WHERE " + pk + " = ?";

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
	public void editar(Produto obj) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "UPDATE " + nomeTable + " SET " + col1 + " = ?," + col2 + " = ?," + col3 + " = ?," + " WHERE "
					+ pk + " = ?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, obj.getNome_produto());
			statement.setDouble(2, obj.getValor());
			statement.setDouble(3, obj.getCategoria().getCod_categoria());

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
	public List<Produto> buscarId(int id) {
		List<Produto> lista = new ArrayList<Produto>();
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "SELECT * FROM " + nomeTable + " tp inner join " + nomeTablecategoria + " tc "
					+ "on tp.tb_categoria_cod_categoria = tc.cod_categoria WHERE tp." + pk + " = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Produto produto = new Produto(
						resultSet.getInt(pk), 
						resultSet.getString(col1),
						resultSet.getDouble(col2),
						new Categoria(
								resultSet.getInt(tc_col1), 
								resultSet.getNString(tc_col2)));
				lista.add(produto);
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
	public List<Produto> listar() {
		List<Produto> lista = new ArrayList<Produto>();

		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "SELECT * FROM " + nomeTable + " tp inner join " + nomeTablecategoria + " tc "
					+ "on tp.tb_categoria_cod_categoria = tc.cod_categoria";

			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Produto produto = new Produto(
						resultSet.getInt(pk), 
						resultSet.getString(col1),
						resultSet.getDouble(col2),
						new Categoria(
								resultSet.getInt(tc_col1), 
								resultSet.getNString(tc_col2)));
				lista.add(produto);
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
