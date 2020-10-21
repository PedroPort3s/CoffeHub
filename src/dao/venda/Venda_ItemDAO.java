package dao.venda;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.interfaces.ICompraVenda;
import entitys.Venda_Item;

public class Venda_ItemDAO implements ICompraVenda<Venda_Item> {

	@Override
	public String Select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int AdicionarItem(int cod, Venda_Item item) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int RemoverItem(int cod, Venda_Item item) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int AlterarQtd(int cod, Venda_Item item) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int AlterarValor(int cod, Venda_Item item) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double TotalItens(int cod) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ProximoSequencial(int cod) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Venda_Item> CarregarItens(int cod) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Venda_Item PreencherItem(ResultSet rs) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
