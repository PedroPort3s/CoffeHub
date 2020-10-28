package control.acesso;

import dao.LoginDAO;

public class ControlAcesso {

	public int CarregarLogin(int cod_pessoa, String senha_funcionario) throws Exception{
		
		int retorno = 0;
		try 
		{			
			if (cod_pessoa <= 0) 
			{
				throw new Exception("Informe o código de acesso");
			}
			
			if (senha_funcionario.equals("")) 
			{
				throw new Exception("Informe a senha de acesso");
			}			
			
			retorno = new LoginDAO().CarregarLogin(cod_pessoa, senha_funcionario);
			if (retorno == 1) 
			{
				retorno = 1;
			}
			else
			{
				retorno = -1;
				throw new Exception("Não foi possível efetuar o login");
			}
								
		} 
		catch (Exception e) 
		{
			throw e;
		}
		
		return retorno;		
	}
}
