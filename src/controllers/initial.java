package controllers;

import java.util.List;

import dao.CategoriaDAO;
import dao.PessoaDAO;
import dao.ProdutoDAO;
import dao.interfaces.ICategoriaDAO;
import dao.interfaces.IPessoaDAO;
import dao.interfaces.IProdutoDAO;
import entitys.Categoria;
import entitys.Pessoa;
import entitys.Produto;

public class initial {

	public static void main(String[] args) {
		/*
		 * fica a dica, se vc definir uma variavel com a interface dela, independente de quantos metodos vc adicione
		 * nunca vai precisar alterar essa variavel, generico é bom
		 */
		
		/* dao categoria
		ICategoriaDAO dao = new CategoriaDAO();
		dao.inserir(new Categoria("Limpeza"));
		dao.deletar(lista.get(0).getCod_categoria());
		Categoria cat = new Categoria(00003, "Vestuário");
		dao.editar(cat);
		List<Categoria> lista = dao.buscarId(00004);
		lista.forEach(categoria -> System.out.println(categoria));
		List<Categoria> lista = dao.listar();
		lista.forEach(categoria -> System.out.println(categoria));
		*/
		/*
		 * dao produto
		IProdutoDAO daop = new ProdutoDAO();
		ICategoriaDAO daoc = new CategoriaDAO();
		List<Categoria> lista = daoc.listar();
		Produto produto = new Produto("produto1", 30.2, lista.get(0));
		daop.inserir(produto);
		daop.deletar(1);
		List<Produto> lista = daop.buscarId(9);
		
		lista.forEach(produto -> System.out.println(produto));
		List<Produto> lista = daop.listar();
		lista.forEach(produto -> System.out.println(produto));
		 */
		
	}

}
