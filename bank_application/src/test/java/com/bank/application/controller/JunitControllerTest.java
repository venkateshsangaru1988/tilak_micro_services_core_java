package com.bank.application.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.application.customexceptions.RecordNotFoundException;

@ExtendWith(MockitoExtension.class)
public class JunitControllerTest {

	@InjectMocks
	private JunitController junitControllerMock;

	@Test
	void nameValidationExceptionTest() {

		assertThrows(RecordNotFoundException.class, () -> junitControllerMock.nameValidation(null));

	}

	@Test
	void nameValidationIfTest() {

		String response = junitControllerMock.nameValidation("Venky");
		assertNotNull(response);

	}
}
