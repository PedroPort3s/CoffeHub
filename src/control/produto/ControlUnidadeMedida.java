package control.produto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.UnidadeMedidaDAO;
import entitys.UnidadeMedida;
import utils.ConexaoMySql;

public class ControlUnidadeMedida {
	public int Inserir(UnidadeMedida unidadeMedida) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;

		try {

			this.ValidarUnidadeMedida(unidadeMedida);

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO catDAO = new UnidadeMedidaDAO(conn);

			retorno = catDAO.Inserir(unidadeMedida);

			conn.close();
		} catch (SQLException ex) {
			throw ex;
		}

		return retorno;
	}

	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		int retorno = 0;

		try {

			if (id <= 0)
				throw new Error("Informe uma ID válida para a exclusão da UnidadeMedida.");

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO catDAO = new UnidadeMedidaDAO(conn);

			retorno = catDAO.Deletar(id);

			conn.close();
		} catch (SQLException ex) {
			throw ex;
		}

		return retorno;
	}

	public int Editar(UnidadeMedida unidade) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;

		try {

			this.ValidarUnidadeMedidaId(unidade);

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO unidadeDAO = new UnidadeMedidaDAO(conn);

			retorno = unidadeDAO.Editar(unidade);

			conn.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}

	public UnidadeMedida Carregar(int id) throws Exception {
		UnidadeMedida unidade = null;
		try {

			if (id <= 0)
				throw new Exception("Informe uma ID válida para carregar uma UnidadeMedida.");

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO unidadeDAO = new UnidadeMedidaDAO(conn);

			unidade = unidadeDAO.Carregar(id);

			conn.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return unidade;
	}

	public List<UnidadeMedida> Listar(String pesquisa) throws Exception {

		List<UnidadeMedida> lstUnidade = null;
		try {

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO unidadeDAO = new UnidadeMedidaDAO(conn);

			if (pesquisa.trim().equals("")) {
				lstUnidade = unidadeDAO.Buscar();
			} else {
				lstUnidade = unidadeDAO.Buscar(pesquisa);
			}

			conn.close();
		} catch (Exception ex) {
			throw ex;
		}

		return lstUnidade;
	}

	private void ValidarUnidadeMedida(UnidadeMedida unidade) throws Exception {
		if (unidade == null)
			throw new Exception("Informe uma UnidadeMedida para a gravação.");
		
		if (unidade.getCod().trim().equals(""))
			throw new Exception("Informe um código para a unidade de medida.");
		
		if (unidade.getCod().length() > 2 || unidade.getCod().length() < 2)
			throw new Exception("Informe um código com dois caracteres para a unidade de medida.");

		if (unidade.getNome().trim().equals(""))
			throw new Exception("Informe um nome para a Unidade de medida.");
		
		if (unidade.getNome().length() < 3)
			throw new Exception("Informe um nome com pelo menos 3 caracteres.");
		
	}

	private void ValidarUnidadeMedidaId(UnidadeMedida unidade) throws Exception {
		if (unidade == null)
			throw new Exception("Informe uma UnidadeMedida para a gravação.");

		if (unidade.getId() <= 0)
			throw new Exception("Informe um código válido para a Unidade de medida.");

		if (unidade.getCod().trim().equals(""))
			throw new Exception("Informe um código para a unidade de medida.");
		
		if (unidade.getCod().length() > 2 || unidade.getCod().length() < 2)
			throw new Exception("Informe um código com dois caracteres para a unidade de medida.");

		if (unidade.getNome().trim().equals(""))
			throw new Exception("Informe um nome para a Unidade de medida.");
		
		if (unidade.getNome().length() < 3)
			throw new Exception("Informe um nome com pelo menos 3 caracteres.");
	}

}
