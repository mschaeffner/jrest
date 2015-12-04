package com.scarabsoft.jrest.test.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/error/500")
public class InternalServerErrorController {

	@RequestMapping(method = RequestMethod.GET)
	public String GET() {
		throw new RuntimeException("test");
	}

}
