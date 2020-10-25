package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.ConexaoMySql;
import utils.enuns.EnumCliente;
import utils.enuns.EnumFuncionario;
import utils.enuns.EnumPessoa;
import dao.interfaces.IFuncionarioDAO;
import entitys.Cliente;
import entitys.Funcionario;
import entitys.Pessoa;



public class FuncionarioDAO implements IFuncionarioDAO {

	@Override
	public void inserir(Funcionario obj) {
		String sqlPessoa = "INSERT INTO "
				+EnumPessoa.pessoa+" ("
					+EnumPessoa.documento+", "
					+EnumPessoa.telefone+", "
					+EnumPessoa.nome+", "
					+EnumPessoa.endereco+", "
					+EnumPessoa.email+") "
						+ "VALUES (?, ?, ?, ?, ?)";
		
		String sqlPessoaIns = "SELECT * FROM "+EnumPessoa.pessoa+" ORDER BY cod DESC LIMIT 1";
		
		
		
		String sqlFuncionario = "INSERT INTO "
				+EnumFuncionario.funcionario+" ("
					+EnumFuncionario.cod_pessoa+", "
					+EnumFuncionario.salario+", "
					+EnumFuncionario.data_contratacao+", "
					+EnumFuncionario.data_demissao+", "
					+EnumFuncionario.senha_funcionario+", "
					+EnumFuncionario.acesso_cod+") "
							+ "VALUES (?, ?, ?, ?, ?, ?)";
		
		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementPessoaIns = connection.prepareStatement(sqlPessoaIns);
				PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario);) {

			
			statementPessoa.setString(1, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(2, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getNome());
			statementPessoa.setString(4, obj.getEndereco());
			statementPessoa.setString(5, obj.getEmail());
			statementPessoa.execute();

			ResultSet resultSet = statementPessoaIns.executeQuery();
			resultSet.next();
			
			statementFuncionario.setInt(1, resultSet.getInt(EnumPessoa.cod.name()));
			statementFuncionario.setDouble(2, obj.getSalario());
			statementFuncionario.setDate(3, Date.valueOf(obj.getData_contratacao()));
			statementFuncionario.setDate(4, Date.valueOf(obj.getData_demissao()));
			statementFuncionario.setString(5, obj.getSenha_funcionario());
			statementFuncionario.setInt(6, obj.getCod_acesso());
			statementFuncionario.execute();

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
		String sqlFuncionario = "delete from " + EnumFuncionario.funcionario + " where " + EnumFuncionario.cod_pessoa + "= ?";
		String sqlPessoa = "delete from " + EnumPessoa.pessoa+ " where " + EnumPessoa.cod + "= ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario);) {

			statementFuncionario.setInt(1, id);
			statementPessoa.setInt(1, id);

			statementFuncionario.execute();
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
	public void editar(Funcionario obj) {
		String sqlPessoa = "UPDATE "+EnumPessoa.pessoa+" SET "+EnumPessoa.documento+"=?, "
				+ EnumPessoa.telefone + "=?, " + EnumPessoa.nome + "=?, " + EnumPessoa.endereco + "=?, "
				+ EnumPessoa.email + "=? " + "WHERE " + EnumPessoa.cod + "= ?";

		String sqlFuncionario = "UPDATE " + EnumFuncionario.funcionario + 
				" SET " + EnumFuncionario.salario + "=?, " +
				" SET " + EnumFuncionario.data_contratacao+ "=?, " +
				" SET " + EnumFuncionario.data_demissao+ "=?, " +
				" SET " + EnumFuncionario.senha_funcionario+ "=?, " +
				" SET " + EnumFuncionario.acesso_cod+ "=? " +
				" WHERE " + EnumFuncionario.cod_pessoa + "= ?";
		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario);) {

			statementPessoa.setString(1, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(2, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getNome());
			statementPessoa.setString(4, obj.getEndereco());
			statementPessoa.setString(5, obj.getEmail());
			statementPessoa.setInt(6, obj.getCod());

			statementFuncionario.setDouble(1, obj.getSalario());
			statementFuncionario.setDate(2, Date.valueOf(obj.getData_contratacao()));
			statementFuncionario.setDate(3, Date.valueOf(obj.getData_demissao()));
			statementFuncionario.setString(4, obj.getSenha_funcionario());
			statementFuncionario.setInt(5, obj.getCod_acesso());
			statementFuncionario.setInt(6, obj.getCod());

			statementFuncionario.execute();
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
	public Funcionario buscarId(Integer id) {
		Funcionario funcionario = new Funcionario();
		String sql= "SELECT * FROM "+EnumPessoa.pessoa+" p "
				+ "inner join "+EnumFuncionario.funcionario+" f "
				+ "on p."+EnumPessoa.cod+"=f."+EnumFuncionario.cod_pessoa+" WHERE p."+EnumPessoa.cod+" = ?";

		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				funcionario = new Funcionario(resultSet.getDate(EnumFuncionario.data_contratacao.name()).toLocalDate(),
						resultSet.getDate(EnumFuncionario.data_demissao.name()).toLocalDate(),
						resultSet.getDouble(EnumFuncionario.salario.name()),
						resultSet.getString(EnumFuncionario.senha_funcionario.name()),
						resultSet.getInt(EnumFuncionario.acesso_cod.name()), 
						resultSet.getInt(EnumPessoa.cod.name()),
						resultSet.getString(EnumPessoa.documento.name()),
						resultSet.getString(EnumPessoa.telefone.name()),
						resultSet.getString(EnumPessoa.nome.name()),
						resultSet.getString(EnumPessoa.endereco.name()),
						resultSet.getString(EnumPessoa.email.name()));
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
		return funcionario;
	}

	@Override
	public List<Funcionario> listar() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		String sql= "SELECT * FROM "+EnumPessoa.pessoa+" p "
				+ "inner join "+EnumFuncionario.funcionario+" f "
				+ "on p."+EnumPessoa.cod+"= f."+EnumFuncionario.cod_pessoa;

		
		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				lista.add(new Funcionario(resultSet.getDate(EnumFuncionario.data_contratacao.name()).toLocalDate(),
								resultSet.getDate(EnumFuncionario.data_demissao.name()).toLocalDate(),
								resultSet.getDouble(EnumFuncionario.salario.name()),
								resultSet.getString(EnumFuncionario.senha_funcionario.name()),
								resultSet.getInt(EnumFuncionario.acesso_cod.name()), 
								resultSet.getInt(EnumPessoa.cod.name()),
								resultSet.getString(EnumPessoa.documento.name()),
								resultSet.getString(EnumPessoa.telefone.name()),
								resultSet.getString(EnumPessoa.nome.name()),
								resultSet.getString(EnumPessoa.endereco.name()),
								resultSet.getString(EnumPessoa.email.name())));
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
