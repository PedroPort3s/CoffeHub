package Helper;

public class Verifica {
	
	public static boolean ehNumeroLong(String numeros) {
		try {
			Long.parseLong(numeros);
	            return true;
		} catch (NumberFormatException e) {	  
	            return false;
		}
	}
	
	public static boolean ehNumeroInt(String numeros) {
		try {
			Integer.parseInt(numeros);
	            return true;
		} catch (NumberFormatException e) {	  
	            return false;
		}
	}
	
	public static boolean ehNumeroFloat(String numeros) {
		try {
			Float.parseFloat(numeros);
	            return true;
		} catch (NumberFormatException e) {	  
	            return false;
		}
	}
	
	public static boolean ehNumeroDouble(String numeros) {
		try 
		{
			Double.parseDouble(numeros);
			return true;			
		} 
		catch (NumberFormatException e)
		{
			return false;
		}
	}
	
}
