package dao.interfaces;

import java.util.List;
/*
 * Aqui eu defini o padrao do dao, todos os daos s�o obrigados a ter esses metodos, usei generics para tal, padrao dao de certo TIPO
 */
public abstract interface IPadraoDAO<T, Y> {

	public void inserir(T obj);
	
	public void deletar(Y id);
	
	public void editar(T obj);
	
	public T buscarId(Y id);
	
	public List<T> listar();
	
}
