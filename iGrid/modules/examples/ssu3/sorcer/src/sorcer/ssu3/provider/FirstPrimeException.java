package sorcer.ssu3.provider;

public class FirstPrimeException extends Exception {

	public FirstPrimeException( Exception cause ) {
		super(cause);
	}

    public FirstPrimeException( String message ) {
        super( message );
    }

    public FirstPrimeException( String message, Throwable cause ) {
        super( message, cause );
    }
}
