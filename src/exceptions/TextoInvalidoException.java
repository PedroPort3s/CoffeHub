package exceptions;

@SuppressWarnings("serial")
public class TextoInvalidoException extends RuntimeException {
	public TextoInvalidoException() { }

	public TextoInvalidoException(String msg) {
		super(msg);
	}
}
