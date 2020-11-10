package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class db {
	
	public static int ProximaID(Connection conexao, String nomeCampo,String nomeTabela) throws Exception {
		
		
		int proxNumero = 0;

		try {		

			PreparedStatement statement = conexao.prepareStatement("select ifnull(max("+nomeCampo+"),0) as 'maior' from "+nomeTabela+"");

			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				proxNumero = resultSet.getInt("maior");
			}
			
			if (proxNumero < 0) throw new Exception ("N�o foi poss�vel recuperar o proximo n�mero da(o) "+nomeTabela+"");
			
			statement.close();
			
			
		} catch (ClassNotFoundException classEx) {
			/* classEx.printStackTrace(); */
			throw classEx;
		} catch (SQLException sqlEx) {
			/* sqlEx.printStackTrace(); */
			throw sqlEx;
		} catch (Exception ex) {
			/* ex.printStackTrace(); */
			throw ex;
		}

		return proxNumero + 1;
	}

	public static void VerificarPeriodo(Date dataIni, Date dataFim) throws Exception {
		if(dataIni == null) throw new Exception("Informe uma data inicial v�lida.");
		
		if(dataFim == null) throw new Exception("Informe uma data final v�lida.");
		
		if(dataFim.before(dataIni)) throw new Exception("A data inicial n�o pode ser maior que a data final.");
	}

}
