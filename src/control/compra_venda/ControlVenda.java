package control.compra_venda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Helper.db;
import dao.venda.VendaDAO;
import dao.venda.Venda_ItemDAO;
import dto.attProdutoDTO;
import entitys.Venda;
import entitys.Venda_Item;
import utils.ConexaoMySql;

public class ControlVenda {
	
	private Connection conexao = null;

	public int Inserir(Venda venda) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaGravar(venda);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			VendaDAO vendaDAO = new VendaDAO(conexao);

			retorno = vendaDAO.Inserir(venda);

			if (retorno <= 0)
				throw new Exception("Erro ao gravar a venda.");

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

	public int Finalizar(Venda venda) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaCod(venda);
			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			VendaDAO vendaDAO = new VendaDAO(conexao);

			if (!vendaDAO.Carregar(venda.getCod()).getStatus().equals("A"))
				throw new Exception("Não é possível finalizar uma venda que não está aberta.");

			if (venda.getItens().size() <= 0)
				throw new Exception("Não é possivel finalizar uma venda que não possui itens");

			List<attProdutoDTO> itensDto = new ArrayList<attProdutoDTO>();

			for (Venda_Item i : venda.getItens()) {
				attProdutoDTO itemDto = new attProdutoDTO(i.getProduto().getCod(),i.getProduto().getDescricao() ,i.getQtd_item());
				itensDto.add(itemDto);
			}

			if (vendaDAO.attVendaProduto(itensDto) != 1)
				throw new Exception("Não foi possivel atualizar o estoque dos itens para prosseguir");

			retorno = vendaDAO.Finalizar(venda);

			if (retorno != 1)
				throw new Exception("Erro ao finalizar a venda.");

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

	public int Deletar(Venda venda) throws Exception {

		int retorno = 0;

		try {

			Venda.ValidarVendaCod(venda);

			if (!venda.getStatus().equals("A")) {
				throw new Exception("Não é possível excluir uma venda que não está aberta.");
			}

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			VendaDAO vendaDAO = new VendaDAO(conexao);

			retorno = new Venda_ItemDAO(conexao).RemoverItens(venda.getCod());

			if (retorno == venda.getItens().size()) {

				retorno = vendaDAO.Deletar(venda.getCod());

				if (retorno != 1)
					throw new Exception("Erro ao deletar a venda.");

			} else {
				throw new Exception("Erro ao deletar os itens. Não foi possível excluir a venda");
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

	public int Editar(Venda venda) throws Exception {
		int retorno = 0;
		try {

			Venda.ValidarVendaCod(venda);

			conexao = ConexaoMySql.getInstance().getConnection();
			conexao.setAutoCommit(false);

			VendaDAO vendaDAO = new VendaDAO(conexao);

			retorno = vendaDAO.Editar(venda);

			if (retorno != 1)
				throw new Exception("Erro ao editar a venda.");

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

	public Venda Carregar(int id) throws Exception {

		Venda venda = null;

		try {

			if (id <= 0)
				throw new Exception("Informe uma ID válida para carregar uma Venda.");

			conexao = ConexaoMySql.getInstance().getConnection();

			VendaDAO vendaDAO = new VendaDAO(conexao);

			venda = vendaDAO.Carregar(id);

			if (venda != null) {
				venda.setItens(new Venda_ItemDAO(conexao).CarregarItens(venda.getCod()));
			}

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		}

		return venda;

	}

	public List<Venda> Listar(Date dataIni, Date dataFim, String statusVenda) throws Exception {
		List<Venda> lstVenda = null;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			VendaDAO VendaDAO = new VendaDAO(conexao);

			db.VerificarPeriodo(dataIni, dataFim);

			if (statusVenda.trim().equals("")) {
				lstVenda = VendaDAO.Buscar(dataIni, dataFim);
			} else {
				lstVenda = VendaDAO.Buscar(dataIni, dataFim, statusVenda);
			}

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return lstVenda;
	}

	public List<Venda> ListarPorPessoa(Date dataIni, Date dataFim, int codigo, String tipoPesquisa) throws Exception {
		List<Venda> lstVenda = null;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			VendaDAO VendaDAO = new VendaDAO(conexao);

			db.VerificarPeriodo(dataIni, dataFim);

			if (!tipoPesquisa.toUpperCase().equals("FUNCIONARIO") || !tipoPesquisa.toUpperCase().equals("CLIENTE")) {
				throw new Exception("Informe se a pesquisa deve ser por funcionário ou cliente.");
			}

			if (codigo <= 0)
				throw new Exception("Informe um código de " + tipoPesquisa.toLowerCase() + " válido para a pesquisa.");

			lstVenda = VendaDAO.Buscar(dataIni, dataFim, codigo, tipoPesquisa);

			if (lstVenda == null || lstVenda.size() <= 0)
				throw new Exception("Não foi encontrado nenhum registro com os parâmetros informados.");

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return lstVenda;
	}

	public double TotalVendasDia(Date data) throws Exception {
		double retorno = 0;
		try {

			conexao = ConexaoMySql.getInstance().getConnection();

			VendaDAO vendaDAO = new VendaDAO(conexao);

			if (data == null)
				throw new Exception("Informe uma data para obter o total de Vendas naquele dia.");

			retorno = vendaDAO.TotalVendasDia(data);

			conexao.close();
		} catch (SQLException ex) {
			throw ex;
		} catch (Exception e) {
			throw e;
		}

		return retorno;
	}
}
