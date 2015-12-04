package com.scibee.freya.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/url")
public class UrlAssembleController {

	@RequestMapping(method = RequestMethod.GET)
	public String GET() {
		return "HelloWorld";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String POST() {
		return "HelloWorld";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String PUT() {
		return "HelloWorld";
	}
}
