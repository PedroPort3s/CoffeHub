package exceptions;

@SuppressWarnings("serial")
public class CampoVazioException extends RuntimeException {

	public CampoVazioException() { }

	public CampoVazioException(String msg) {
		super(msg);
	}
}
