package dao.interfaces;

public interface ICompraVenda {
	public int AdicionarItem(int cod, int codProduto);
	public int RemoverItem(int cod, int codProduto);
	public int AlterarQtd(int cod, int codProduto, double quantidade);
	public int AlterarValor(int cod, int codProduto, double valor);
}
