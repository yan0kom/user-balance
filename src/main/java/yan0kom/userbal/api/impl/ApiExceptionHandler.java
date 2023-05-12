package yan0kom.userbal.api.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import yan0kom.userbal.api.dto.ErrorOutDto;
import yan0kom.userbal.domain.exception.EntityNotFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice("yan0kom.userbal.api.impl")
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String msg = ex.getBindingResult().getFieldErrors().stream()
			.map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
			.collect(Collectors.joining("; "));
		ErrorOutDto validationError = new ErrorOutDto(String.format("Validation error: %s", msg));

		return handleExceptionInternal(ex, validationError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorOutDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		String msg = "Username \"" +	ex.getMessage() + "\" not found";
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorOutDto(msg));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorOutDto> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorOutDto(ex.getMessage()));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ErrorOutDto> handleIllegalStateException(IllegalStateException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorOutDto(ex.getMessage()));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorOutDto> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorOutDto(ex.getMessage()));
	}
}
