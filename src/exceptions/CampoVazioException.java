package exceptions;

public class CampoVazioException extends RuntimeException {

	public CampoVazioException() { }

	public CampoVazioException(String msg) {
		super(msg);
	}
}
