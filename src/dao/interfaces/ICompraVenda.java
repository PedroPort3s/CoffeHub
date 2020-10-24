package dao.interfaces;

import java.sql.SQLException;

public interface ICompraVenda<T> extends IPadraoDB<T> {
	public int Finalizar(T obj) throws SQLException, Exception;
}
