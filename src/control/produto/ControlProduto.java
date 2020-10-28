package control.produto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ProdutoDAO;
import entitys.Produto;
import utils.ConexaoMySql;

public class ControlProduto {
	
	public int Inserir(Produto produto) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;
		try {
			
			this.ValidarProdutoGravar(produto);
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			retorno = prodDAO.Inserir(produto);
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return retorno;
	}
	
	
	
	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		int retorno = 0;
		try {
			
			if(id <= 0) throw new Error("Informe uma ID válida para a exclusão do produto.");
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			retorno = prodDAO.Deletar(id);
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return retorno;
	}
	
	public int Editar(Produto prod) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;
		try {
			
			this.ValidarProdutoId(prod);
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			retorno = prodDAO.Editar(prod);
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		catch(Exception e)
		{
			throw e;
		}
		
		
		return retorno;
	}

	public Produto Carregar(int id) throws ClassNotFoundException, SQLException, Exception {
		Produto prod = null;
		try {
			
			if(id <= 0) throw new Error("Informe uma ID válida para carregar um produto.");
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			try
			{
				prod = prodDAO.Carregar(id);
			} 
			catch (Exception e) {
				throw e;
			}
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return prod;
	}

	public List<Produto> Listar(String pesquisa) throws ClassNotFoundException, SQLException {
		List<Produto> lstProd = null;
		try {		
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			
			if(pesquisa.trim().equals("")) 
			{
				lstProd = prodDAO.Buscar();
			}
			else
			{
				lstProd = prodDAO.Buscar(pesquisa);
			}
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return lstProd;
	}

	public List<Produto> Listar() throws ClassNotFoundException, SQLException {
		List<Produto> lstProd = null;
		try {
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			lstProd = prodDAO.Buscar();
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return lstProd;
	}
	
	public List<Produto> ListarEmCategoria(String pesquisa, int codCategoria) throws ClassNotFoundException, SQLException {
		List<Produto> lstProd = null;
		try {
			
			if(codCategoria < 0) throw new Error("Informe uma categoria para listar seus produtos");
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			
			ProdutoDAO prodDAO = new ProdutoDAO(conn);
			
			lstProd = prodDAO.Buscar_Produtos_e_Categoria(pesquisa, codCategoria);
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return lstProd;
	}
	
private void ValidarProdutoGravar(Produto prod) throws Exception {
		
		if(prod == null) throw new Exception("Informe um produto para a gravação.");
			
		if(prod.getDescricao().trim().equals("")) throw new Exception("Informe uma descrição para o produto.");
		
		if(prod.getDescricao().trim().length() < 3) throw new Exception("Informe uma descrição com pelo menos 3 caracteres.");
		
		if(prod.getQtd_atual() <= 0) throw new Exception("A quantidade não pode ser menor ou igual a zero.");
		
		if(prod.getValor_un() <= 0) throw new Exception("O valor unitário não pode ser menor ou igual a zero.");
		
		if(prod.getUnidadeMedida().equals("")) throw new Exception("Informe uma unidade de medida para o produto.");
		
		if(prod.getUnidadeMedida().length() > 2) throw new Exception("A unidade de medida deve ser de 2 caracteres");
		
		if(prod.getCategoria() == null) throw new Exception("Informe uma categoria para o produto");
		
		if(prod.getCategoria().getCod() <= 0) throw new Exception("Informe um código válido para a categoria.");
		
		if(prod.getCategoria().getNome().trim().equals("")) throw new Exception("Nome inválido para categoria.");		
		
	}
	
	public void ValidarProdutoId(Produto prod) throws Exception{
		if(prod == null) throw new Exception("Informe um produto para a gravação.");
		
		if(prod.getCod() <= 0) throw new Exception("Informe uma ID válida para o produto");
		
		if(prod.getDescricao().trim().equals("")) throw new Exception("Informe uma descrição para o produto.");
		
		if(prod.getQtd_atual() <= 0) throw new Exception("A quantidade não pode ser menor ou igual a zero.");
		
		if(prod.getValor_un() <= 0) throw new Exception("O valor unitário não pode ser menor ou igual a zero.");
		
		if(prod.getUnidadeMedida().equals("")) throw new Exception("Informe uma unidade de medida para o produto.");
		
		if(prod.getUnidadeMedida().length() > 2) throw new Exception("A unidade de medida deve ser de 2 caracteres");
		
		if(prod.getCategoria() == null) throw new Exception("Informe uma categoria para o produto");
		
		if(prod.getCategoria().getCod() <= 0) throw new Exception("Informe um código válido para a categoria.");
		
		if(prod.getCategoria().getNome().trim().equals("")) throw new Exception("Nome inválido para categoria.");		
		
	}

	

}
