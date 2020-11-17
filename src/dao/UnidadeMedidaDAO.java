package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.interfaces.IPadraoDB;
import entitys.UnidadeMedida;

public class UnidadeMedidaDAO implements IPadraoDB<UnidadeMedida>{
	
	private Connection conexao = null;
	
	public UnidadeMedidaDAO(Connection conn) {
		conexao = conn;
	}

	@Override
	public int Inserir(UnidadeMedida obj) throws ClassNotFoundException, SQLException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Editar(UnidadeMedida obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UnidadeMedida Carregar(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnidadeMedida> Buscar(String pesquisa) throws ClassNotFoundException, SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnidadeMedida> Buscar() throws ClassNotFoundException, SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
