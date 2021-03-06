package dao.venda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.interfaces.ICompraVenda;
import dto.attProdutoDTO;
import entitys.Venda;
import utils.db;

public class VendaDAO implements ICompraVenda<Venda> {

	private Connection conexao = null;

	public VendaDAO(Connection conn) {
		conexao = conn;
	}

	private String Select_Venda() {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select cod, valor_total, data_origem, data_confirmacao, status, cod_Cliente, cod_Funcionario from venda");
		return sql.toString();
	}

	@Override
	public int Inserir(Venda venda) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;

		try {

			String sql = "INSERT INTO venda(cod, data_origem, data_confirmacao, valor_total, status, cod_Cliente, cod_Funcionario) VALUES (?,?,?,?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			retorno = this.ProximoCodVenda();
			statement.setInt(1, retorno);
			statement.setString(2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			statement.setString(3, null);
			statement.setDouble(4, venda.TotalVenda());
			statement.setString(5, "A");
			statement.setInt(6, venda.getCliente().getCod());
			statement.setInt(7, venda.getFuncionario().getCod());

			if (statement.executeUpdate() != 1)
				throw new Exception("N?o foi poss?vel gravar uma venda.");

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

			String sql = "DELETE FROM venda WHERE cod= " + id;

			PreparedStatement statement = conexao.prepareStatement(sql);

			retorno = statement.executeUpdate();

			statement.close();

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return retorno;
	}

	@Override
	public int Editar(Venda venda) throws ClassNotFoundException, SQLException {
		int retorno = 0;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE venda SET");
			sql.append(" valor_total = " + venda.TotalVenda() + ",");
			sql.append(" cod_Cliente = " + venda.getCliente().getCod());
			sql.append(" WHERE cod = " + venda.getCod());

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			retorno = statement.executeUpdate();
			statement.close();

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return retorno;
	}

	@Override
	public Venda Carregar(int id) throws Exception {
		Venda venda = null;

		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_Venda());
			sql.append(" where cod =" + id);

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				venda = this.PreencherVenda(resultSet);
			}

			statement.close();

		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}

		return venda;
	}

	@Override
	public List<Venda> Buscar(String status) throws Exception {
		List<Venda> lista = new ArrayList<Venda>();

		try {

			StringBuilder sql = new StringBuilder();

			sql.append(this.Select_Venda());
			sql.append(" where status='" + status + "'");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Venda venda = this.PreencherVenda(resultSet);
				lista.add(venda);
			}

			statement.close();

		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}

	@Override
	public List<Venda> Buscar() throws Exception {
		List<Venda> lista = new ArrayList<Venda>();

		try {

			StringBuilder sql = new StringBuilder();

			sql.append(this.Select_Venda());

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Venda venda = this.PreencherVenda(resultSet);
				lista.add(venda);
			}

			statement.close();

		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}

	public List<Venda> Buscar(Date DataInicio, Date DataFim, String status, int codFuncionario, int codCliente)
			throws Exception {
		List<Venda> lista = new ArrayList<Venda>();

		try {
			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_Venda());
			sql.append(" where data_origem <= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataFim) + "'");
			sql.append(" and data_origem >= '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DataInicio) + "'");

			if (!status.equals("")) {
				sql.append(" and status = '" + status + "'");
			}

			if (codFuncionario > 0) {
				sql.append(" and cod_Funcionario=" + codFuncionario);
			}

			if (codCliente > 0) {
				sql.append(" and cod_Cliente=" + codCliente);
			}

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Venda venda = this.PreencherVenda(resultSet);
				lista.add(venda);
			}

			statement.close();

		} catch (ClassNotFoundException classEx) {
			classEx.printStackTrace();
			throw classEx;
		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return lista;
	}

	public double TotalVendasDia(Date data) throws SQLException {

		double retorno = 0;

		try {

			String sum = "select sum(valor_total) as 'totalVendas' from venda where data_confirmacao='"
					+ new SimpleDateFormat("yyyy-MM-dd").format(data) + "' and status='F' ";
			PreparedStatement statement = conexao.prepareStatement(sum);
			ResultSet resultSet;
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				retorno = resultSet.getInt("totalVendas");
			}

			if (retorno < 0)
				throw new Error("N?o foi poss?vel recuperar o total das vendas no dia " + data.toString());

			statement.close();
		} catch (SQLException e) {
			throw e;
		}

		return retorno;
	}

	private int ProximoCodVenda() throws Exception {
		return db.ProximaID(conexao, "cod", "venda");
	}

	public Venda PreencherVenda(ResultSet resultSet) throws Exception {
		Venda venda = new Venda();

		venda.setCod(resultSet.getInt("cod"));
		venda.setData_origem(resultSet.getDate("data_origem"));
		venda.setData_confirmacao(resultSet.getDate("data_confirmacao"));
		venda.setStatus(resultSet.getString("status"));
		venda.setItens(new Venda_ItemDAO(conexao).CarregarItens(venda.getCod()));
		venda.setCliente(new ClienteDAO().buscarId(resultSet.getInt("cod_Cliente")));
		venda.setFuncionario(new FuncionarioDAO().buscarId(resultSet.getInt("cod_Funcionario")));

		return venda;
	}

	@Override
	public int Finalizar(Venda venda) throws Exception {

		int retorno = 0;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE venda SET");
			sql.append(" valor_total = " + venda.TotalVenda() + ",");
			sql.append(" data_confirmacao = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',");
			sql.append(" status = 'F'");
			sql.append(" WHERE cod = " + venda.getCod());

			PreparedStatement statement = conexao.prepareStatement(sql.toString());

			retorno = statement.executeUpdate();

			if (retorno != 1)
				throw new Exception("N?o foi poss?vel finalizar a venda.");

			statement.close();

		} catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return retorno;
	}

	public int attVendaProduto(List<attProdutoDTO> listaDTO) throws Exception {
		int retorno = 0;
		try {
			for (attProdutoDTO att : listaDTO) {
				String sqlQtdAtual = "select qtd_atual from produto where cod_produto=" + att.getCodProduto();
				PreparedStatement qtdAtual = conexao.prepareStatement(sqlQtdAtual);

				ResultSet resultSet = qtdAtual.executeQuery();

				while (resultSet.next()) {

					double qtd = resultSet.getDouble("qtd_atual");

					if ((qtd - att.getQtdProduto()) < 0) {
						throw new Exception("Produto:" + att.getCodProduto() + " - " + att.getNomeProduto()
								+ " quantidade no estoque:" + qtd + " quantidade da venda: " + att.getQtdProduto()
								+ ". Quantidade de venda incompat?vel com o estoque.");
					}
				}

				String sql = ("UPDATE Produto set qtd_atual = qtd_atual - " + att.getQtdProduto()
						+ " where cod_produto=" + att.getCodProduto());

				PreparedStatement statement = conexao.prepareStatement(sql);
				retorno = statement.executeUpdate();
				statement.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return retorno;
	}
}
