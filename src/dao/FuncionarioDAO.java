package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IFuncionarioDAO;
import entitys.Funcionario;
import utils.ConexaoMySql;
import utils.enuns.EnumFuncionario;
import utils.enuns.EnumPessoa;

public class FuncionarioDAO implements IFuncionarioDAO {

	@Override
	public void inserir(Funcionario obj) {
		String sqlPessoa = "INSERT INTO " + EnumPessoa.pessoa + " (" + EnumPessoa.documento + ", " + EnumPessoa.telefone
				+ ", " + EnumPessoa.nome + ", " + EnumPessoa.endereco + ", " + EnumPessoa.email + ") "
				+ "VALUES (?, ?, ?, ?, ?)";

		String sqlPessoaIns = "SELECT * FROM " + EnumPessoa.pessoa + " ORDER BY cod DESC LIMIT 1";

		String sqlFuncionario = "INSERT INTO " + EnumFuncionario.funcionario + " (" + EnumFuncionario.cod_pessoa + ", "
				+ EnumFuncionario.salario + ", " + EnumFuncionario.data_contratacao + ", "
				+ EnumFuncionario.senha_funcionario + ", " + EnumFuncionario.cod_acesso + ") "
				+ "VALUES (?, ?, ?, ?, ?)";

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
			statementFuncionario.setString(4, obj.getSenha_funcionario());
			statementFuncionario.setInt(5, obj.getCod_acesso());
			statementFuncionario.execute();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public boolean verificaRG(String rg) {

		String sql = "SELECT * FROM " + EnumPessoa.pessoa + " WHERE " + EnumPessoa.documento + " = ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setString(1, rg);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public void deletar(Integer id) {
		String sqlFuncionario = "delete from " + EnumFuncionario.funcionario + " where " + EnumFuncionario.cod_pessoa
				+ "= ?";
		String sqlPessoa = "delete from " + EnumPessoa.pessoa + " where " + EnumPessoa.cod + "= ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario);) {

			statementFuncionario.setInt(1, id);
			statementPessoa.setInt(1, id);

			statementFuncionario.execute();
			statementPessoa.execute();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void editar(Funcionario obj) {
		String sqlPessoa = "UPDATE " + EnumPessoa.pessoa + " SET " + EnumPessoa.documento + "=?, " + EnumPessoa.telefone
				+ "=?, " + EnumPessoa.nome + "=?, " + EnumPessoa.endereco + "=?, " + EnumPessoa.email + "=? " + "WHERE "
				+ EnumPessoa.cod + "= ?";

		String sqlFuncionario;
		if (obj.getSenha_funcionario() != null) {
			sqlFuncionario = "UPDATE " + EnumFuncionario.funcionario + " SET " + EnumFuncionario.salario + "=?, "
					+ EnumFuncionario.data_contratacao + "=?, " + EnumFuncionario.senha_funcionario
					+ "=?, " + EnumFuncionario.cod_acesso + "=? " + " WHERE " + EnumFuncionario.cod_pessoa
					+ "= ?";

		} else {
			sqlFuncionario = "UPDATE " + EnumFuncionario.funcionario + " SET " + EnumFuncionario.salario + "=?, "
					+ EnumFuncionario.data_contratacao + "=?, " + EnumFuncionario.cod_acesso + "=? "
					+ " WHERE " + EnumFuncionario.cod_pessoa + "= ?";
		}

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementPessoa = connection.prepareStatement(sqlPessoa);
				PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario);) {

			statementPessoa.setString(1, obj.getDocumento().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(2, obj.getTelefone().replaceAll("[^0-9]+", ""));
			statementPessoa.setString(3, obj.getNome());
			statementPessoa.setString(4, obj.getEndereco());
			statementPessoa.setString(5, obj.getEmail());
			statementPessoa.setInt(6, obj.getCod());

			if (obj.getSenha_funcionario() != null) {
				statementFuncionario.setDouble(1, obj.getSalario());
				statementFuncionario.setDate(2, Date.valueOf(obj.getData_contratacao()));
				statementFuncionario.setString(3, obj.getSenha_funcionario());
				statementFuncionario.setInt(4, obj.getCod_acesso());
				statementFuncionario.setInt(5, obj.getCod());
			} else {
				statementFuncionario.setDouble(1, obj.getSalario());
				statementFuncionario.setDate(2, Date.valueOf(obj.getData_contratacao()));
				statementFuncionario.setInt(3, obj.getCod_acesso());
				statementFuncionario.setInt(4, obj.getCod());
			}

			statementFuncionario.execute();
			statementPessoa.execute();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	public void editarDemissao(int cod) {
		String sqlFuncionario = "UPDATE " + EnumFuncionario.funcionario + " SET " + EnumFuncionario.data_demissao
				+ "=? " + " WHERE " + EnumFuncionario.cod_pessoa + "= ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statementFuncionario = connection.prepareStatement(sqlFuncionario);) {

			statementFuncionario.setDate(1, Date.valueOf(LocalDate.now()));
			statementFuncionario.setInt(2, cod);

			statementFuncionario.execute();

		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	@Override
	public Funcionario buscarId(Integer id) {
		Funcionario funcionario = new Funcionario();
		String sql = "SELECT * FROM " + EnumPessoa.pessoa + " p " + "inner join " + EnumFuncionario.funcionario + " f "
				+ "on p." + EnumPessoa.cod + "=f." + EnumFuncionario.cod_pessoa + " WHERE p." + EnumPessoa.cod + " = ?";

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				if (resultSet.getDate(EnumFuncionario.data_demissao.name()).toLocalDate() == null) {
					funcionario = new Funcionario(
							resultSet.getDate(EnumFuncionario.data_contratacao.name()).toLocalDate(), null,
							resultSet.getDouble(EnumFuncionario.salario.name()),
							resultSet.getString(EnumFuncionario.senha_funcionario.name()),
							resultSet.getInt(EnumFuncionario.cod_acesso.name()),
							resultSet.getInt(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.endereco.name()),
							resultSet.getString(EnumPessoa.email.name()));
				} else {
					funcionario = new Funcionario(
							resultSet.getDate(EnumFuncionario.data_contratacao.name()).toLocalDate(),
							resultSet.getDate(EnumFuncionario.data_demissao.name()).toLocalDate(),
							resultSet.getDouble(EnumFuncionario.salario.name()),
							resultSet.getString(EnumFuncionario.senha_funcionario.name()),
							resultSet.getInt(EnumFuncionario.cod_acesso.name()),
							resultSet.getInt(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.endereco.name()),
							resultSet.getString(EnumPessoa.email.name()));
				}
			}
		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return funcionario;
	}

	@Override
	public List<Funcionario> listar() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		String sql = "SELECT * FROM " + EnumPessoa.pessoa + " p" + " inner join " + EnumFuncionario.funcionario + " f "
				+ "on p." + EnumPessoa.cod + "=" + EnumFuncionario.cod_pessoa;

		try (Connection connection = ConexaoMySql.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);) {

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				if (resultSet.getDate(EnumFuncionario.data_demissao.name()) == null) {
					lista.add(new Funcionario(resultSet.getDate(EnumFuncionario.data_contratacao.name()).toLocalDate(),
							null, resultSet.getDouble(EnumFuncionario.salario.name()),
							resultSet.getString(EnumFuncionario.senha_funcionario.name()),
							resultSet.getInt(EnumFuncionario.cod_acesso.name()),
							resultSet.getInt(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.endereco.name()),
							resultSet.getString(EnumPessoa.email.name())));
				} else {
					lista.add(new Funcionario(resultSet.getDate(EnumFuncionario.data_contratacao.name()).toLocalDate(),
							resultSet.getDate(EnumFuncionario.data_demissao.name()).toLocalDate(),
							resultSet.getDouble(EnumFuncionario.salario.name()),
							resultSet.getString(EnumFuncionario.senha_funcionario.name()),
							resultSet.getInt(EnumFuncionario.cod_acesso.name()),
							resultSet.getInt(EnumPessoa.cod.name()), resultSet.getString(EnumPessoa.documento.name()),
							resultSet.getString(EnumPessoa.telefone.name()),
							resultSet.getString(EnumPessoa.nome.name()),
							resultSet.getString(EnumPessoa.endereco.name()),
							resultSet.getString(EnumPessoa.email.name())));
				}
			}
		} catch (ClassNotFoundException classException) {
			classException.printStackTrace();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return lista;
	}

}
