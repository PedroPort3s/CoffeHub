package entitys;

import java.util.ArrayList;

import javax.naming.directory.InvalidAttributesException;

public class Acesso {

	private static ArrayList<Acesso> listaAcessos;

	private int cod;

	private String funcao;

	public Acesso() {
		super();
	}

	public Acesso(int cod, String funcao) {
		super();
		this.cod = cod;
		this.funcao = funcao;
	}

	public static ArrayList<Acesso> listaAcessos() {
		if (listaAcessos == null) {
			listaAcessos = new ArrayList<Acesso>();
			listaAcessos.add(new Acesso(1, "Administrador"));
			listaAcessos.add(new Acesso(2, "Vendedor/Balconista"));
			listaAcessos.add(new Acesso(3, "Estoquista"));
		}
		return listaAcessos;
	}
	
	public static Acesso acharAcesso(int cod) {
		Acesso acesso = new Acesso();
		for(Acesso a : listaAcessos()) {
			if(a.getCod() == cod)
				acesso = a;
		}
		return acesso;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	@Override
	public String toString() {
		return funcao;
	}
}
