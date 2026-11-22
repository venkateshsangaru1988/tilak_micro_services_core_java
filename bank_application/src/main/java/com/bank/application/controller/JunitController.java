package com.bank.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.customexceptions.RecordNotFoundException;

@RestController
@RequestMapping("/junit")
public class JunitController {

	@GetMapping("/test/{name}")
	public String nameValidation(@PathVariable String name) {

		if (name == null) {
			throw new RecordNotFoundException("Name is required", 404);
		}

		if ("Venky".equalsIgnoreCase(name)) {
			name = "Hello " + name;
		}

		else if ("Tilak".equalsIgnoreCase(name)) {
			name = "Hello,Welocome  " + name;
		} else {
			name = "Hello,Welocome,Good morning  " + name;
		}

		return name;
	}

}
