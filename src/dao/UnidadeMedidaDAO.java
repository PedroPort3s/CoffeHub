package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.IPadraoDB;
import entitys.UnidadeMedida;
import utils.Verifica;
import utils.db;

public class UnidadeMedidaDAO implements IPadraoDB<UnidadeMedida> {

	private Connection conexao = null;

	public UnidadeMedidaDAO(Connection conn) {
		conexao = conn;
	}

	private String Select_UnidadeMedida() {
		return "select id, codUnidade, nome, permiteFracionada from UnidadeMedida";
	}

	@Override
	public int Inserir(UnidadeMedida un) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;

		try {

			String sql = "INSERT INTO UnidadeMedida(id, codUnidade, nome, permiteFracionada) VALUES (?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			un.setId(db.ProximaID(conexao, "id", "UnidadeMedida"));
			statement.setInt(1, un.getId());
			statement.setString(2, un.getCod());
			statement.setString(3, un.getNome());
			statement.setString(4, un.getPermiteFracionado() ? "S" : "N");
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
	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		int retorno = 0;
		try {

			String sql = "DELETE FROM unidadeMedida WHERE id= ?";

			PreparedStatement statement = conexao.prepareStatement(sql);

			statement.setInt(1, id);
			retorno = statement.executeUpdate();

			statement.close();
		} catch (SQLException sqlEx) {
			/* int erro = sqlEx.getErrorCode(); */
			if (sqlEx.getErrorCode() == 1451) {
				throw new SQLException(
						"Esta unidade de medida já esta sendo utilizada por um ou mais produtos, para deletar a mesma, favor deletar os produtos antes.");
			} else {
				throw sqlEx;
			}
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		return retorno;
	}

	@Override
	public int Editar(UnidadeMedida un) throws ClassNotFoundException, SQLException {
		int retorno = 0;
		try {

			String sql = "UPDATE unidadeMedida SET codUnidade = ?, nome = ?, permiteFracionada = ? WHERE id = ?";

			PreparedStatement statement = conexao.prepareStatement(sql);

			statement.setString(1, un.getCod());
			statement.setString(2, un.getNome());
			statement.setString(3, un.getPermiteFracionado() ? "S" : "N");
			statement.setInt(4, un.getId());

			retorno = statement.executeUpdate();
			statement.close();

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
	public UnidadeMedida Carregar(int id) throws Exception {

		UnidadeMedida unidadeMedida = null;

		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_UnidadeMedida());
			sql.append(" where id=" + id);

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				unidadeMedida = this.PreencherUnidade(resultSet);
			}

			statement.close();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return unidadeMedida;
	}
	
	public UnidadeMedida Carregar(String codigo) throws Exception {

		UnidadeMedida unidadeMedida = null;

		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_UnidadeMedida());
			sql.append(" where codUnidade='" + codigo + "'");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				unidadeMedida = this.PreencherUnidade(resultSet);
			}

			statement.close();
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return unidadeMedida;
	}

	@Override
	public List<UnidadeMedida> Buscar(String pesquisa) throws ClassNotFoundException, SQLException, Exception {
		List<UnidadeMedida> lista = new ArrayList<UnidadeMedida>();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_UnidadeMedida());

			if (Verifica.ehNumeroInt(pesquisa)) {
				sql.append(" where id=" + pesquisa);
			} else {
				sql.append(" where nome like ('%" + pesquisa + "%') or codUnidade like ('%" + pesquisa + "%')");
			}

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				lista.add(this.PreencherUnidade(resultSet));
			}

			statement.close();
		} catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		return lista;
	}

	@Override
	public List<UnidadeMedida> Buscar() throws ClassNotFoundException, SQLException, Exception {
		List<UnidadeMedida> lista = new ArrayList<UnidadeMedida>();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_UnidadeMedida());

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				lista.add(this.PreencherUnidade(resultSet));
			}

			statement.close();
		} catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}
		return lista;
	}

	private UnidadeMedida PreencherUnidade(ResultSet result) throws SQLException {
		
		
		return new UnidadeMedida(result.getInt("id"), 
				result.getString("codUnidade"), 
				result.getString("nome"),
				result.getString("permiteFracionada").equals("S") ? true : false);
	}

}
