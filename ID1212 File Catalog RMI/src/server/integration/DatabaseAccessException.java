package server.integration;

public class DatabaseAccessException extends Exception{
	
    public DatabaseAccessException(String reason) {
        super(reason);
    }

    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public DatabaseAccessException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }

}
