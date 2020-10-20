package dao.venda;

import java.sql.SQLException;
import java.util.List;

import dao.interfaces.IPadraoDB;

public class VendaDAO implements IPadraoDB<Venda> {

	@Override
	public int Inserir(Venda obj) throws ClassNotFoundException, SQLException, Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Deletar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int Editar(Venda obj) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Venda Carregar(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Venda> Buscar(String pesquisa) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Venda> Buscar() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
