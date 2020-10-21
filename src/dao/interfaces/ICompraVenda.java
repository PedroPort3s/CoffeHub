package dao.interfaces;

import java.sql.SQLException;

public interface ICompraVenda<T> {
	public String Select();
	public int AdicionarItem(int cod, T item) throws SQLException;
	public int RemoverItem(int cod, T item) throws SQLException;
	public int AlterarQtd(int cod, T item);
	public int AlterarValor(int cod, T item);
	public double TotalItens(int cod);
	public int ProximoSequencial(int cod);
}
