package dao.interfaces;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import entitys.Compra;

public interface IPadraoDB<T> {
	
	public int Inserir(T obj)throws ClassNotFoundException, SQLException, Exception;
	
	public int Deletar(int id)throws ClassNotFoundException, SQLException;
	
	public int Editar(T obj)throws ClassNotFoundException, SQLException;
	
	public T Carregar(int id)throws Exception;
	
	public List<T> Buscar(String pesquisa)throws ClassNotFoundException, SQLException, Exception;
	
	public List<T> Buscar() throws ClassNotFoundException, SQLException, Exception;	
}
