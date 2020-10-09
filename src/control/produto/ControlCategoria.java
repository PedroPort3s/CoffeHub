package control.produto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.CategoriaDAO;
import entitys.Categoria;
import utils.ConexaoMySql;

public class ControlCategoria {
	public int Inserir(Categoria cat) throws ClassNotFoundException, SQLException, Exception {
		int retorno = 0;
		
		try {
				
			this.ValidarCategoria(cat);
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			CategoriaDAO catDAO = new CategoriaDAO(conn);
				
			retorno = catDAO.Inserir(cat);
				
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
				
			if(id <= 0) throw new Error("Informe uma ID válida para a exclusão da categoria.");
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			CategoriaDAO catDAO = new CategoriaDAO(conn);
				
			retorno = catDAO.Deletar(id);
				
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return retorno;
	}
	
	public int Editar(Categoria cat) throws ClassNotFoundException, SQLException {
		int retorno = 0;
		
		try {
			
			this.ValidarCategoriaId(cat);
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			CategoriaDAO catDAO = new CategoriaDAO(conn);
			
			retorno = catDAO.Editar(cat);
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return retorno;
	}

	public Categoria Carregar(int id) throws ClassNotFoundException, SQLException {
		Categoria cat = null;
		try {
			
			if(id <= 0) throw new Error("Informe uma ID válida para carregar uma categoria.");
			
			Connection conn = ConexaoMySql.getInstance().getConnection();
			CategoriaDAO catDAO = new CategoriaDAO(conn);
			
			cat = catDAO.Carregar(id);
			
			conn.close();
		}
		catch(SQLException ex){
			throw ex;
		}
		
		return cat;
	}

	public List<Categoria> Listar(String pesquisa) throws ClassNotFoundException, SQLException {
	
			List<Categoria> lstCat = null;
			try {
				
				Connection conn = ConexaoMySql.getInstance().getConnection();
				CategoriaDAO catDAO = new CategoriaDAO(conn);
				
				if(pesquisa.trim().equals("")) {
					lstCat = catDAO.Buscar();
				}
				else {
					lstCat = catDAO.Buscar(pesquisa);
				}
				
				
				conn.close();
			}
			catch(SQLException ex){
				throw ex;
			}
			
			return lstCat;
	}
	
	private void ValidarCategoria(Categoria cat) {
		if(cat == null) throw new Error("Informe uma categoria para a gravação.");
		
		if(cat.getNome().trim().equals("")) throw new Error("Informe um nome para a categoria.");
	}
	
	private void ValidarCategoriaId(Categoria cat) {
		if(cat == null) throw new Error("Informe uma categoria para a gravação.");
		
		if(cat.getCod() <= 0) throw new Error("Informe um código válido para a categoria.");
		
		if(cat.getNome().trim().equals("")) throw new Error("Informe um nome para a categoria.");
	}
}
