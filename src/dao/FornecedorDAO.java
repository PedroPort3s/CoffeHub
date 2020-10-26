package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IFornecedorDAO;
import entitys.Cliente;
import entitys.Fornecedor;
import utils.ConexaoMySql;
import utils.enuns.EnumCliente;
import utils.enuns.EnumFornecedor;
import utils.enuns.EnumPessoa;

public class FornecedorDAO implements IFornecedorDAO {

	@Override
	public void inserir(Fornecedor obj) {
		String sqlPessoa = "INSERT INTO " + EnumPessoa.pessoa + " (" + EnumPessoa.documento + ", " + EnumPessoa.telefone
				+ ", " + EnumPessoa.nome + ", " + EnumPessoa.endereco + ", " + EnumPessoa.email + ") "
				+ "VALUES (?, ?, ?, ?, ?)";

		String sqlPessoaIns = "SELECT * FROM " + EnumPessoa.pessoa + " ORDER BY cod DESC LIMIT 1";

		String sqlFornecedor = "INSERT INTO " + EnumFornecedor.fornecedor + " (" + EnumFornecedor.cod_pessoa + ", "
				+ EnumFornecedor.data_contrato + ") " + "VALUES (?, ?)";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementPessoaIns = connection.prepareStatement(sqlPessoaIns);
				PreparedStatement statementFornecedor = connection.prepareStatement(sqlFornecedor);) {
			
			statementPessoa.setString(1, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(2, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getNome());
			statementPessoa.setString(4, obj.getEndereco());
			statementPessoa.setString(5, obj.getEmail());
			statementPessoa.execute();

			ResultSet resultSet = statementPessoaIns.executeQuery();
			resultSet.next();

			statementFornecedor.setInt(1, resultSet.getInt(EnumPessoa.cod.name()));
			statementFornecedor.setDate(2, Date.valueOf(obj.getData_contrato()));
			statementFornecedor.execute();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
			System.out.println(
					"esse erro vai acontecer por conta do connector, pesquisem sobre" + " \n----  erro no inserir");
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
		String sqlFornecedor = "delete from " + EnumFornecedor.fornecedor + " where " + EnumFornecedor.cod_pessoa
				+ "= ?";
		String sqlPessoa = "delete from " + EnumPessoa.pessoa + " where " + EnumPessoa.cod + "= ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementFornecedor = connection.prepareStatement(sqlFornecedor);) {

			statementFornecedor.setInt(1, id);
			statementPessoa.setInt(1, id);

			statementFornecedor.execute();
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
	public void editar(Fornecedor obj) {
		String sqlPessoa = "UPDATE " + EnumPessoa.pessoa + " SET " + EnumPessoa.documento + "=?, " + EnumPessoa.telefone
				+ "=?, " + EnumPessoa.nome + "=?, " + EnumPessoa.endereco + "=?, " + EnumPessoa.email + "=? " + "WHERE "
				+ EnumPessoa.cod + "= ?";

		String sqlFornecedor = "UPDATE " + EnumFornecedor.fornecedor + " SET " + EnumFornecedor.data_contrato
				+ "= ? WHERE " + EnumFornecedor.cod_pessoa + "= ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementFornecedor = connection.prepareStatement(sqlFornecedor);) {

			statementPessoa.setString(1, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(2, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getNome());
			statementPessoa.setString(4, obj.getEndereco());
			statementPessoa.setString(5, obj.getEmail());
			statementPessoa.setInt(6, obj.getCod());

			statementFornecedor.setDate(1, Date.valueOf(obj.getData_contrato()));
			statementFornecedor.setInt(2, obj.getCod());

			statementFornecedor.execute();
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

	public boolean verificaRG(String rg) {

		String sql= "SELECT * FROM "+EnumPessoa.pessoa+" WHERE "+EnumPessoa.documento+" = ?";

		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setString(1, rg);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
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
		return false;
	}
	
	@Override
	public Fornecedor buscarId(Integer id) {
		Fornecedor fornecedor = null;
		String sql = "SELECT * FROM " + EnumPessoa.pessoa + " p " + "inner join " + EnumFornecedor.fornecedor + " f "
				+ "on p." + EnumPessoa.cod + "= f." + EnumFornecedor.cod_pessoa + " WHERE p." + EnumPessoa.cod + " = ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if(resultSet.next()) {
				fornecedor = new Fornecedor(resultSet.getDate(EnumFornecedor.data_contrato.name()).toLocalDate(),
						resultSet.getInt(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
						resultSet.getString(EnumPessoa.telefone.name()), resultSet.getString(EnumPessoa.nome.name()),
						resultSet.getString(EnumPessoa.endereco.name()), resultSet.getString(EnumPessoa.email.name()));
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
		return fornecedor;
	}

	@Override
	public List<Fornecedor> listar() {
		List<Fornecedor> lista = new ArrayList<Fornecedor>();
		String sql= "SELECT * FROM "+EnumPessoa.pessoa+" p "
				+ "inner join "+EnumFornecedor.fornecedor+" f "
				+ "on p."+EnumPessoa.cod+"=f."+EnumFornecedor.cod_pessoa;

		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				lista.add(new Fornecedor(resultSet.getDate(EnumFornecedor.data_contrato.name()).toLocalDate(),
						resultSet.getInt(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
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
