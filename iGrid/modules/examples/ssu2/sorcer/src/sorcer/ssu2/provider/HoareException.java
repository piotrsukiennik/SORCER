package sorcer.ssu2.provider;

public class HoareException extends Exception {

	public HoareException( Exception cause ) {
		super(cause);
	}

    public HoareException( String message ) {
        super( message );
    }

    public HoareException( String message, Throwable cause ) {
        super( message, cause );
    }
}
