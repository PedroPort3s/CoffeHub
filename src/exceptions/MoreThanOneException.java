package exceptions;

/*
 * s� brincando por aqui
 */
public class MoreThanOneException extends RuntimeException {
	
	public MoreThanOneException() {	}
	
	public MoreThanOneException(String msg) {
		super(msg);
	}
	
}
