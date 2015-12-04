package com.scibee.freya.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scibee.freya.test.domain.IP;

@RestController
@RequestMapping("/v1/simple/ip")
public class SimpleController {

	@RequestMapping(method = RequestMethod.GET)
	public IP GET() {
		return new IP.Builder().ip("127.0.0.1").requestDate(System.currentTimeMillis()).getCountry("DE").build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public IP POST() {
		return new IP.Builder().ip("127.0.0.1").requestDate(System.currentTimeMillis()).getCountry("DE").build();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public IP PUT() {
		return new IP.Builder().ip("127.0.0.1").requestDate(System.currentTimeMillis()).getCountry("DE").build();
	}

}
