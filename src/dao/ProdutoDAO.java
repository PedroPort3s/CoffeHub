package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Helper.Verifica;
import dao.interfaces.IProdutoDAO;
import entitys.Produto;
import utils.ConexaoMySql;

public class ProdutoDAO implements IProdutoDAO {
	
	private Connection conexao = null;
	
	public ProdutoDAO(Connection conn) {
		this.conexao = conn;
	}
	
	private String Select_ProdutoCategoria() {
		StringBuilder sql = new StringBuilder();
		sql.append("select p.cod_produto, p.nome_produto, p.valor_un, p.qtd_atual, p.un_medida, c.cod, c.nome");
		sql.append(" from produto as p inner join categoria as c on c.cod = p.categoria_cod");
		return sql.toString();
	}

	@Override
	public int Inserir(Produto prod) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;
		
		try {
			String sql = "INSERT INTO produto(cod_produto,nome_produto,valor_un,qtd_atual,categoria_cod,un_medida) VALUES (?,?,?,?,?,?)";

			PreparedStatement statement = conexao.prepareStatement(sql);
			
			statement.setInt(1, this.ProximoNumeroProduto());
			statement.setString(2, prod.getDescricao());
			statement.setDouble(3, prod.getValor_un());
			statement.setInt(4, prod.getQtd_atual());
			statement.setInt(5, prod.getCategoria().getCod());
			statement.setString(6, prod.getUnidadeMedida());
			
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
			
			String sql = "DELETE FROM produto WHERE cod_produto= " + id;

			PreparedStatement statement = conexao.prepareStatement(sql);
			
			retorno = statement.executeUpdate();
			
			statement.close();
			
		}  catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return retorno;
	}

	@Override
	public int Editar(Produto prod) throws SQLException {
		int retorno = 0;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE produto SET");
			sql.append(" nome_produto = '" + prod.getDescricao() + "',");
			sql.append("valor_un = "+prod.getValor_un()+",");
			sql.append("qtd_atual = "+prod.getQtd_atual()+",");
			sql.append("Categoria_cod = "+prod.getCategoria().getCod() + ",");
			sql.append("un_medida = '"+prod.getUnidadeMedida()+"'");
			sql.append(" WHERE cod_produto = "+prod.getCod());

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
	public Produto Carregar(int id) throws Exception {
		Produto produto = null;
		
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_ProdutoCategoria());
			sql.append(" where p.cod_produto ="+id);			

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				produto = this.PreencherProduto(resultSet);
			}
			
			statement.close();
			
		}  catch (SQLException sqlEx) {
			sqlEx.printStackTrace();
			throw sqlEx;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		
		return produto;
	}

	@Override
	public List<Produto> Buscar(String pesquisa) throws ClassNotFoundException, SQLException {
		
		List<Produto> lista = new ArrayList<Produto>();
		
		try {

			StringBuilder sql = new StringBuilder();
			
			sql.append(this.Select_ProdutoCategoria());
			
			if(Verifica.ehNumeroInt(pesquisa)){
				sql.append(" where cod_produto="+pesquisa);
			}
			else{	
				sql.append(" where nome_produto like ('%"+pesquisa+"%')");
			}

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Produto prod = this.PreencherProduto(resultSet);
				lista.add(prod);
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
	
	public List<Produto> Buscar_Produtos_e_Categoria(String pesquisa, int codCategoria) throws ClassNotFoundException, SQLException {
		List<Produto> lista = new ArrayList<Produto>();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_ProdutoCategoria());
			
			if(Verifica.ehNumeroInt(pesquisa)){
				sql.append(" where cod_produto="+pesquisa);
			}
			else{	
				sql.append(" where nome_produto like ('%"+pesquisa+"%')");
			}
			
			if(codCategoria > 0) {
				sql.append("and c.cod="+codCategoria);
			}

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Produto prod = this.PreencherProduto(resultSet);
				lista.add(prod);
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
	public List<Produto> Buscar() throws ClassNotFoundException, SQLException {
		List<Produto> lista = new ArrayList<Produto>();
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(this.Select_ProdutoCategoria());

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			
			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				Produto prod = this.PreencherProduto(resultSet);
				lista.add(prod);
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
	
	public Produto PreencherProduto(ResultSet resultSet) throws SQLException, ClassNotFoundException {
		Produto prod = new Produto();
		prod.setCod(resultSet.getInt("p.cod_produto"));
		prod.setDescricao(resultSet.getString("p.nome_produto"));
		prod.setValor_un(resultSet.getDouble("p.valor_un"));
		prod.setQtd_atual(resultSet.getInt("p.qtd_atual"));
		prod.setUnidadeMedida(resultSet.getString("p.un_medida"));
		prod.setCategoria(new CategoriaDAO(conexao).Carregar(resultSet.getInt("c.cod")));
		return prod;
	}
	
	private int ProximoNumeroProduto() throws Exception {
		
		
		int numProduto = 0;

		try {	

			PreparedStatement statement = conexao.prepareStatement("select ifnull(max(cod_produto),0) as 'maior' from produto");

			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
			numProduto = resultSet.getInt("maior");
			}
			
			if (numProduto < 0) throw new Exception ("Não foi possível recuperar o proximo número dos produtos");
			
			statement.close();
			
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

		return numProduto + 1;
	}


}
