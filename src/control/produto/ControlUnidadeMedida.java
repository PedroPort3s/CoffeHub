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

	public int Editar(UnidadeMedida cat) throws ClassNotFoundException, SQLException {
		int retorno = 0;

		try {

			this.ValidarUnidadeMedidaId(cat);

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO catDAO = new UnidadeMedidaDAO(conn);

			retorno = catDAO.Editar(cat);

			conn.close();
		} catch (SQLException ex) {
			throw ex;
		}

		return retorno;
	}

	public UnidadeMedida Carregar(int id) throws Exception {
		UnidadeMedida cat = null;
		try {

			if (id <= 0)
				throw new Error("Informe uma ID válida para carregar uma UnidadeMedida.");

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO catDAO = new UnidadeMedidaDAO(conn);

			cat = catDAO.Carregar(id);

			conn.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return cat;
	}

	public List<UnidadeMedida> Listar(String pesquisa) throws Exception {

		List<UnidadeMedida> lstCat = null;
		try {

			Connection conn = ConexaoMySql.getInstance().getConnection();
			UnidadeMedidaDAO catDAO = new UnidadeMedidaDAO(conn);

			if (pesquisa.trim().equals("")) {
				lstCat = catDAO.Buscar();
			} else {
				lstCat = catDAO.Buscar(pesquisa);
			}

			conn.close();
		} catch (Exception ex) {
			throw ex;
		}

		return lstCat;
	}

	private void ValidarUnidadeMedida(UnidadeMedida unidade) {
		if (unidade == null)
			throw new Error("Informe uma UnidadeMedida para a gravação.");

		if (unidade.getNome().trim().equals(""))
			throw new Error("Informe um nome para a Unidade de medida.");
	}

	private void ValidarUnidadeMedidaId(UnidadeMedida unidade) {
		if (unidade == null)
			throw new Error("Informe uma UnidadeMedida para a gravação.");

		if (unidade.getId() <= 0)
			throw new Error("Informe um código válido para a Unidade de medida.");

		if (unidade.getNome().trim().equals(""))
			throw new Error("Informe um nome para a Unidade de medida.");
	}

}
