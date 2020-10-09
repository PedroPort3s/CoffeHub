package Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class db {
	
private static int ProximaID(Connection conexao, String nomeCampo,String nomeTabela) throws Exception {
		
		
		int numeroCategoria = 0;

		try {		

			PreparedStatement statement = conexao.prepareStatement("select ifnull(max("+nomeCampo+"),0) as 'maior' from "+nomeTabela+"");

			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next()) {
				numeroCategoria = resultSet.getInt("maior");
			}
			
			if (numeroCategoria < 0) throw new Exception ("Não foi possível recuperar o proximo número da(o) "+nomeTabela+"");
			
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

		return numeroCategoria + 1;
	}

}
