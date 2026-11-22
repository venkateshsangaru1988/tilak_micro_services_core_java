package com.restaurant.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restaurant.exceptionshandler.RestaurantBaseApplicationUtilException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestaurantExceptionHandler {

	@ExceptionHandler(RestaurantBaseApplicationUtilException.class)
	public ResponseEntity<ErrorResponse> handleRestaurantException(RestaurantBaseApplicationUtilException ex,
			HttpServletRequest request) {

		HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());

		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(status).body(errorResponse);
	}

}
