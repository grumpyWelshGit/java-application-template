package uk.org.landeg.jat.api.error;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ClientException{

	public BadRequestException(String message) {
		super(message, HttpStatus.BAD_REQUEST);
	}

	private static final long serialVersionUID = 2431098459209732151L;

}
