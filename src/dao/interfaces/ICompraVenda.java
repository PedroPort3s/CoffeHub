package dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ICompraVenda<T> {
	public String Select();
	public int AdicionarItem(int cod, T item) throws SQLException;
	public int RemoverItem(int cod, T item) throws SQLException;
	public int AlterarQtd(int cod, T item) throws SQLException;
	public int AlterarValor(int cod, T item) throws SQLException;
	public double TotalItens(int cod) throws SQLException;
	public List<T> CarregarItens(int cod) throws Exception;
	T PreencherItem(ResultSet rs) throws Exception;
	int ProximoSequencial(int cod) throws SQLException;
}
