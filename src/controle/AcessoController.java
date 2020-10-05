package controle;

import dao.LoginDAO;

public class AcessoController {

	public int CarregarLogin(int cod_pessoa, String senha_funcionario) throws Exception{
		
		int retorno = 0;
		try 
		{			
			if (cod_pessoa <= 0) 
			{
				throw new Exception("Informe o código de acesso");
			}
			
			if (senha_funcionario == "") 
			{
				throw new Exception("Informe a senha de acesso");
			}
			
			retorno = new LoginDAO().CarregarLogin(cod_pessoa, senha_funcionario);
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return retorno;		
	}
}
