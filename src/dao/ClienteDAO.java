package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IClienteDAO;
import entitys.Cliente;
import entitys.Pessoa;
import utils.ConexaoMySql;
import utils.enuns.EnumCliente;
import utils.enuns.EnumPessoa;

public class ClienteDAO implements IClienteDAO {

	@Override
	public void inserir(Cliente obj) {
		String sqlPessoa = "INSERT INTO "
				+EnumPessoa.pessoa+" ("
					+EnumPessoa.cod+", "
					+EnumPessoa.documento+", "
					+EnumPessoa.telefone+", "
					+EnumPessoa.nome+", "
					+EnumPessoa.endereco+", "
					+EnumPessoa.email+") "
						+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		String sqlCliente = "INSERT INTO "
				+EnumCliente.cliente+" ("
					+EnumCliente.cod_pessoa+", "
					+EnumCliente.data_nascimento+") "
							+ "VALUES (?, ?)";
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementCliente = connection.prepareStatement(sqlCliente);) {

			statementPessoa.setString(1, obj.getCod());
			statementPessoa.setString(2, obj.getDocumento());
			statementPessoa.setString(3, obj.getTelefone());
			statementPessoa.setString(4, obj.getNome());
			statementPessoa.setString(5, obj.getEndereco());
			statementPessoa.setString(6, obj.getEmail());

			statementCliente.setString(1, obj.getCod());
			statementCliente.setString(2, obj.getData_nascimentoString());

			statementPessoa.execute();
			statementCliente.execute();

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
	public void deletar(String id) {
		String sqlCliente = "delete from " + EnumCliente.cliente + " where " + EnumCliente.cod_pessoa + "= ?";
		String sqlPessoa = "delete from " + EnumPessoa.pessoa+ " where " + EnumPessoa.cod + "= ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementCliente = connection.prepareStatement(sqlCliente);) {

			statementCliente.setString(1, id);
			statementPessoa.setString(1, id);

			statementCliente.execute();
			statementPessoa.execute();

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
	public void editar(Cliente obj) {
		String sqlPessoa = "UPDATE "+EnumPessoa.pessoa+" SET "+EnumPessoa.documento+"=?, "
				+ EnumPessoa.telefone + "=?, " + EnumPessoa.nome + "=?, " + EnumPessoa.endereco + "=?, "
				+ EnumPessoa.email + "=? " + "WHERE " + EnumPessoa.cod + "= ?";

		String sqlCliente = "UPDATE " + EnumCliente.cliente + " SET " + EnumCliente.data_nascimento + "=? WHERE "
				+ EnumCliente.cod_pessoa + "= ?";
		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementCliente = connection.prepareStatement(sqlCliente);) {

			statementPessoa.setString(1, obj.getDocumento());
			statementPessoa.setString(2, obj.getTelefone());
			statementPessoa.setString(3, obj.getNome());
			statementPessoa.setString(4, obj.getEndereco());
			statementPessoa.setString(5, obj.getEmail());
			statementPessoa.setString(6, obj.getCod());

			statementCliente.setString(1, obj.getData_nascimentoString());
			statementCliente.setString(2, obj.getCod());

			statementCliente.execute();
			statementPessoa.execute();

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
	public List<Cliente> buscarId(String id) {

		List<Cliente> lista = new ArrayList<Cliente>();
		String sql= "SELECT * FROM "+EnumPessoa.pessoa+" p "
				+ "inner join "+EnumCliente.cliente+" c "
				+ "on p."+EnumPessoa.cod+"="+EnumCliente.cod_pessoa+" WHERE p."+EnumPessoa.cod+" = ?";

		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				lista.add(new Cliente(resultSet.getDate(EnumCliente.data_nascimento.name()),
						resultSet.getString(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
						resultSet.getString(EnumPessoa.telefone.name()), resultSet.getString(EnumPessoa.nome.name()),
						resultSet.getString(EnumPessoa.endereco.name()), resultSet.getString(EnumPessoa.email.name())));
			}
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
	public List<Cliente> listar() {
		List<Cliente> lista = new ArrayList<Cliente>();
		String sql= "SELECT * FROM "+EnumPessoa.pessoa+" p "
				+ "inner join "+EnumCliente.cliente+" c "
				+ "on p."+EnumPessoa.cod+"="+EnumCliente.cod_pessoa;

		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				lista.add(new Cliente(resultSet.getDate(EnumCliente.data_nascimento.name()),
						resultSet.getString(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
						resultSet.getString(EnumPessoa.telefone.name()), resultSet.getString(EnumPessoa.nome.name()),
						resultSet.getString(EnumPessoa.endereco.name()), resultSet.getString(EnumPessoa.email.name())));
			}
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

}
