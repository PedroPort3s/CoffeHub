package control.compra_venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.compra.CompraDAO;
import dao.compra.Compra_ItemDAO;
import dto.attProdutoDTO;
import entitys.Compra;
import entitys.Compra_Item;
import utils.ConexaoMySql;
import utils.db;

public class ControlCompra {

	private Connection conexao = null;

	public int Inserir(Compra compra) throws Exception {

		int retorno = 0;

		try {

			Compra.ValidarCompraGravar(compra);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);
			CompraDAO compraDAO = new CompraDAO(conexao);

			retorno = compraDAO.Inserir(compra);

			if (retorno <= 0)
				throw new Exception("Erro ao gravar a compra.");

			conexao.commit();
			conexao.close();
		} catch (SQLException ex) {
			if (conexao != null)
				conexao.rollback();
			throw ex;
		} catch (Exception e) {
			if (conexao != null)
				conexao.rollback();
			throw e;
		}

		return retorno;
	}

	public int Finalizar(Compra compra) throws Exception {

		int retorno = 0;

		try {

			Compra.ValidarCompraCod(compra);
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			CompraDAO compraDAO = new CompraDAO(conexao);

			if (!compraDAO.Carregar(compra.getCod()).getStatus().equals("E"))
				throw new Exception("N?o ? poss?vel finalizar uma compra que n?o est? enviada.");

			if (compra.getItens().size() <= 0)
				throw new Exception("N?o ? possivel finalizar uma compra que n?o possui itens");

			List<attProdutoDTO> itensDto = new ArrayList<attProdutoDTO>();

			for (Compra_Item i : compra.getItens()) {
				attProdutoDTO itemDto = new attProdutoDTO(i.getProduto().getCod(), i.getQtd_item());
				itensDto.add(itemDto);
			}

			if (new CompraDAO(conexao).attCompraProduto(itensDto) != 1)
				throw new Exception("N?o foi possivel atualizar o estoque dos itens para prosseguir");

			retorno = compraDAO.Finalizar(compra);

			if (retorno != 1)
				throw new Exception("Erro ao finalizar a compra.");

			conexao.commit();
			conexao.close();
		} catch (SQLException ex) {
			if (conexao != null)
				conexao.rollback();

			throw ex;
		} catch (Exception e) {
			if (conexao != null)
				conexao.rollback();
			throw e;
		}

		return retorno;
	}

	public int Enviar(Compra compra) throws Exception {

		int retorno = 0;

		try {

			Compra.ValidarCompraCod(compra);

			if (compra.getItens().size() <= 0)
				throw new Exception("N?o ? possivel enviar uma compra que n?o possui itens");

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			CompraDAO compraDAO = new CompraDAO(conexao);

			if (!compraDAO.Carregar(compra.getCod()).getStatus().equals("A"))
				throw new Exception("N?o ? poss?vel enviar uma compra que n?o est? aberta.");

			retorno = compraDAO.Enviar(compra);

			if (retorno != 1)
				throw new Exception("Erro ao enviar a compra.");

			conexao.commit();
			conexao.close();
		} catch (SQLException ex) {
			if (conexao != null)
				conexao.rollback();

			throw ex;
		} catch (Exception e) {
			if (conexao != null)
				conexao.rollback();
			throw e;
		}

		return retorno;
	}

	public int Deletar(Compra compra) throws Exception {

		int retorno = 0;

		try {

			Compra.ValidarCompraCod(compra);

			if (!compra.getStatus().equals("A")) {
				throw new Exception("N?o ? poss?vel excluir uma venda que n?o est? aberta.");
			}

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			CompraDAO compraDAO = new CompraDAO(conexao);

			retorno = new Compra_ItemDAO(conexao).RemoverItens(compra.getCod());

			if (retorno == compra.getItens().size()) {

				retorno = compraDAO.Deletar(compra.getCod());

				if (retorno != 1)
					throw new Exception("Erro ao deletar a compra.");

			} else {
				throw new Exception("Erro ao deletar os itens. N?o foi poss?vel excluir a compra");
			}

			conexao.commit();
			conexao.close();
		} catch (SQLException ex) {
			if (conexao != null)
				conexao.rollback();

			throw ex;
		} catch (Exception e) {
			if (conexao != null)
				conexao.rollback();
			throw e;
		}

		return retorno;
	}

	public int Editar(Compra compra) throws Exception {
		int retorno = 0;
		try {

			Compra.ValidarCompraCod(compra);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			CompraDAO compraDAO = new CompraDAO(conexao);

			retorno = compraDAO.Editar(compra);

			if (retorno != 1)
				throw new Exception("Erro ao editar a compra.");

			conexao.commit();
			conexao.close();
		} catch (SQLException ex) {
			if (conexao != null)
				conexao.rollback();

			throw ex;
		} catch (Exception e) {
			if (conexao != null)
				conexao.rollback();
			throw e;
		}

		return retorno;
	}

	public Compra Carregar(int id) throws Exception {

		Compra compra = null;

		try {

			if (id <= 0)
				throw new Exception("Informe uma ID v?lida para carregar uma compra.");

			conexao = ConexaoMySql.getInstance().getConnection();

			CompraDAO compraDAO = new CompraDAO(conexao);

			compra = compraDAO.Carregar(id);

			if (compra != null) {
				compra.setItens(new Compra_ItemDAO(conexao).CarregarItens(compra.getCod()));
			}

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		}

		return compra;

	}

	public List<Compra> Listar(Date dataIni, Date dataFim, String statusCompra) throws Exception {
		List<Compra> lstCompra = null;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			CompraDAO compraDAO = new CompraDAO(conexao);

			db.VerificarPeriodo(dataIni, dataFim);

			if (statusCompra.trim().equals("")) {
				lstCompra = compraDAO.Buscar(dataIni, dataFim);
			} else {
				lstCompra = compraDAO.Buscar(dataIni, dataFim, statusCompra);
			}

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return lstCompra;
	}

	public List<Compra> Listar(Date dataIni, Date dataFim, String status, int codFuncionario, int codFornecedor)
			throws Exception {
		List<Compra> lstCompra = null;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			CompraDAO compraDAO = new CompraDAO(conexao);

			db.VerificarPeriodo(dataIni, dataFim);

			if (!status.equals("")) {
				if (!status.equals("A") && !status.equals("F") && !status.equals("E"))
					throw new Exception("Informe o status A (Aberto), E (Enviado) ou F (Finalizado).");
			}

			lstCompra = compraDAO.Buscar(dataIni, dataFim, status, codFuncionario, codFornecedor);

			if (lstCompra == null || lstCompra.size() <= 0)
				throw new Exception("N?o foi encontrado nenhum registro com os par?metros informados.");

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return lstCompra;
	}

	public double TotalComprasDia(Date data) throws Exception {
		double retorno = 0;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			CompraDAO compraDAO = new CompraDAO(conexao);

			if (data == null)
				throw new Exception("Informe uma data para obter o total de compras naquele dia.");

			retorno = compraDAO.TotalComprasDia(data);

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}

}
