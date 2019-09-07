package uk.org.landeg.jat.api.error;

import java.util.UUID;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 5995301662276433252L;

	private final ErrorType type;

	private final String reference;

	public ApplicationException(String message, ErrorType type) {
		super(message);
		this.type = type;
		this.reference = UUID.randomUUID().toString();
	}

	public ErrorType getType() {
		return type;
	} 
	
	public String getReference() {
		return reference;
	}
}
