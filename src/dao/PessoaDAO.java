package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import dao.interfaces.IPessoaDAO;
import entitys.Pessoa;
import exceptions.MoreThanOneException;
import utils.ConexaoMySql;
/*
 * Aqui eu s� puxei a classe com o padr�o l�, consigo adicionar mais classes e tals, e fica a dica, quando
 */
public class PessoaDAO implements IPessoaDAO {

	
	/*
	 * statement � o carinha onde vc aponta os values ( ? ) basicamente vc executa
	 * os comando sqls por aqui o execute, executa o comando, close fecha essa
	 * conex�o com o banco
	 */

	private String nomeTable = "pessoa";
	
	@Override
	public void inserir(Pessoa obj) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "INSERT INTO Pessoa(ID_Pessoa, nome) VALUES (?, ?)";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setInt(1, obj.getCod());
			statement.setString(2, obj.getNome());
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
	public void deletar(Integer id) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "DELETE FROM Pessoa WHERE ID_Pessoa = ?";

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
	public void editar(Pessoa obj) {
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "UPDATE Pessoa SET NOME = ? WHERE ID_Pessoa = ?";

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
	public List<Pessoa> buscarId(Integer id) {

		List<Pessoa> lista = new ArrayList<Pessoa>();
		try {
			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "SELECT * FROM pessoa WHERE ID_Pessoa = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
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
	public List<Pessoa> listar() {

		List<Pessoa> listaPessoas = new ArrayList<Pessoa>();

		try {

			Connection connection = ConexaoMySql.getInstance().getConnection();

			String sql = "SELECT * FROM Pessoa";

			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

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

		return listaPessoas;
	}
}
