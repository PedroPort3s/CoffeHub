package entitys;

public class Funcionario_Acesso {

	private Acesso acesso;

	private char usuarioAcessa;

	public boolean usuarioTemAcesso() {
		return false;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public char getUsuarioAcessa() {
		return usuarioAcessa;
	}

	public void setUsuarioAcessa(char usuarioAcessa) {
		this.usuarioAcessa = usuarioAcessa;
	}

}
