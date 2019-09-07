package uk.org.landeg.jat.api.error;

public class ErrorResponseModel {
	private final String message;
	private final ErrorType type;
	private final String reference;
	
	
	public ErrorResponseModel(String message, ErrorType type, String reference) {
		super();
		this.message = message;
		this.type = type;
		this.reference = reference;
	}

	public String getMessage() {
		return message;
	}
	public ErrorType getType() {
		return type;
	}
	public String getReference() {
		return reference;
	}
	
	
}
