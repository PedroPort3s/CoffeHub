package control.compra_venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.venda.Venda_ItemDAO;
import entitys.Venda;
import entitys.Venda_Item;
import utils.ConexaoMySql;

public class ControlVendaItens {

	private Connection conexao = null;

	public ControlVendaItens(Connection conn) {
		this.conexao = conn;
	}

	public ControlVendaItens() {
		super();
	}

	public int AdicionarItem(Venda venda, Venda_Item item) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaCod(venda);
			Venda_Item.ValidarVendaItem(item);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			Venda_ItemDAO VendaItemDAO = new Venda_ItemDAO(conexao);

			retorno = VendaItemDAO.AdicionarItem(venda.getCod(), item);

			if (retorno != 1)
				throw new Exception("Erro ao gravar o item: " + item.getProduto().getCod() + " - "
						+ item.getProduto().getDescricao() + " na venda.");

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

	public int RemoverItem(Venda venda, Venda_Item item) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaCod(venda);
			Venda_Item.ValidarVendaItem(item);

			if(venda.getStatus() == "F") {
				throw new Exception("Não é possivel remover um item de uma venda finalizada");
			}
			
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			Venda_ItemDAO vendaItemDAO = new Venda_ItemDAO(conexao);

			retorno = vendaItemDAO.RemoverItem(venda.getCod(), item);

			if (retorno != 1)
				throw new Exception("Erro ao remover o item: " + item.getProduto().getCod() + " - "
						+ item.getProduto().getDescricao() + " na venda.");

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

	public int AlterarQuantidadeItem(Venda venda, Venda_Item item) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaCod(venda);
			Venda_Item.ValidarVendaItem(item);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			Venda_ItemDAO vendaItemDAO = new Venda_ItemDAO(conexao);

			retorno = vendaItemDAO.AlterarQtd(venda.getCod(), item);

			if (retorno != 1)
				throw new Exception("Erro ao alterar a quantidade do item: " + item.getProduto().getCod() + " - "
						+ item.getProduto().getDescricao() + " na venda.");

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

	public int AlterarValorUnitarioItem(Venda venda, Venda_Item item) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaCod(venda);
			Venda_Item.ValidarVendaItem(item);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			Venda_ItemDAO VendaItemDAO = new Venda_ItemDAO(conexao);

			retorno = VendaItemDAO.AlterarValor(venda.getCod(), item);

			if (retorno != 1)
				throw new Exception("Erro ao alterar o valor unitário do item: " + item.getProduto().getCod() + " - "
						+ item.getProduto().getDescricao() + " na venda.");

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

	public List<Venda_Item> CarregarItensDaVenda(Venda venda) throws Exception {
		List<Venda_Item> lstVendaItem = null;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			Venda_ItemDAO VendaItemDAO = new Venda_ItemDAO(conexao);

			Venda.ValidarVendaCod(venda);

			lstVendaItem = VendaItemDAO.CarregarItens(venda.getCod());

			if (lstVendaItem == null || lstVendaItem.size() <= 0)
				throw new Exception("Não foi encontrado nenhum registro com os parâmetros informados.");

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return lstVendaItem;
	}
}
