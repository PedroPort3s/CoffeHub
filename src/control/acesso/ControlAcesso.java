package control.acesso;

import dao.LoginDAO;
import entitys.Funcionario;

public class ControlAcesso {

	public Funcionario CarregarLogin(int cod_pessoa, String senha_funcionario) throws Exception{
		
		Funcionario funcionario = null;
		
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
			
			funcionario = new LoginDAO().CarregarLogin(cod_pessoa, senha_funcionario);
			
			if (funcionario != null) 
			{
				if(funcionario.getData_demissao() != null) 
					throw new Exception("O funcionário informado consta como demitido no sistema.");
			}
			else
			{
				throw new Exception("Não foi possível efetuar o login, verifique seu usuario e senha.");
			}
								
		} 
		catch (Exception e) 
		{
			throw e;
		}
		
		return funcionario;		
	}
}
