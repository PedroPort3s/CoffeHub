package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entitys.Funcionario;
import utils.ConexaoMySql;

public class LoginDAO extends ConexaoMySql{

	// Carregar usuario e retornar 1 para autorizar acesso
	// Retorno diferente de 1 significa acesso incorreto ou usuario invalido	
	
	final String SQl_SELECT_FUNCIONARIO = "SELECT select f.cod_Pessoa, f.salario, f.data_Contratacao, f.data_Demissao, f.senha_funcionario from funcionario FROM funcionario where f.cod_pessoa = ? and f.senha_funcionario = ?";
	
	public Funcionario CarregarLogin(int cod_pessoa, String senha_funcionario) throws Exception
	{	
		Funcionario funcionario = null;
		int retorno = 0;
			
		try
		{
			Connection connection = ConexaoMySql.getInstance().getConnection();
			
			PreparedStatement pst = connection.prepareStatement(SQl_SELECT_FUNCIONARIO);

			pst.setInt(1, cod_pessoa);
			pst.setString(2, senha_funcionario);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				funcionario = new Funcionario();
				funcionario.setCod(rs.getInt("cod_pessoa"));
				funcionario.setSenha_funcionario("senha_funcionario");
							
				if(funcionario.getCod() > 0 && !funcionario.getSenha_funcionario().equals(""))
				{
					funcionario = new FuncionarioDAO().buscarId(funcionario.getCod());
				}
				else
				{
					funcionario = null;
				}
			}

		}
		catch (Exception e)
		{
			throw e;
		}
		
	
		return funcionario;
	}
}

