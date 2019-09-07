package uk.org.landeg.jat.api.error;

import org.springframework.http.HttpStatus;

public class ClientException extends ApplicationException{

	private static final long serialVersionUID = -6057612466456051010L;

	private final HttpStatus responseStatus;

	public ClientException(String message, HttpStatus responseStatus) {
		super(message, ErrorType.CLIENT);
		this.responseStatus = responseStatus;
	}
	
	public HttpStatus getResponseStatus() {
		return responseStatus;
	}
}
