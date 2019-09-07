package uk.org.landeg.jat.api;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import uk.org.landeg.jat.api.error.ApplicationException;
import uk.org.landeg.jat.api.error.ClientException;
import uk.org.landeg.jat.api.error.ErrorResponseModel;

@RestControllerAdvice
public class ApiErrorHandler {
	
	@Order(100)
	@ExceptionHandler(ClientException.class)
	public ResponseEntity<ErrorResponseModel> handleApplicationExeption(ClientException e) {
		final ErrorResponseModel response = new ErrorResponseModel(e.getMessage(), e.getType(), e.getReference());
		return ResponseEntity.status(e.getResponseStatus())
				.body(response);
	}

	@Order(9999)
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponseModel handleApplicationExeption(ApplicationException e) {
		final ErrorResponseModel response = new ErrorResponseModel(e.getMessage(), e.getType(), e.getReference());
		return response;
	}
	
}
