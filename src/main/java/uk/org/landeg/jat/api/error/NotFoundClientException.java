package uk.org.landeg.jat.api.error;

import org.springframework.http.HttpStatus;

public class NotFoundClientException extends ClientException{

	public NotFoundClientException(String message) {
		super(message, HttpStatus.NOT_FOUND);
	}

	private static final long serialVersionUID = 1149681502228391821L;

}
