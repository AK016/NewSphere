package com.masai.exceptions;

public class DuplicateNewsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateNewsException(String message) {
        super(message);
    }
}
