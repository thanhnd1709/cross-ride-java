package com.crossover.techtrial.exceptions;

public class PersonException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int status;
	
	public PersonException(int status) {
        super();
        this.status = status;
    }

    public PersonException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    public PersonException(String message, int status) {
        super(message);
        this.status = status;
    }

    public PersonException(Throwable cause, int status) {
        super(cause);
        this.status = status;
    }

	
}
