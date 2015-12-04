package com.scibee.freya.test;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scibee.freya.test.domain.IP;

@RestController
@RequestMapping("/v1/collection/ip")
public class CollectionSimpleController {

	@RequestMapping(method = RequestMethod.GET)
	public Collection<IP> GET() {
		return col();
	}

	@RequestMapping(method = RequestMethod.POST)
	public Collection<IP> POST() {
		return col();
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Collection<IP> PUT() {
		return col();
	}

	private Collection<IP> col() {
		final Collection<IP> result = new LinkedList<>();
		result.add(new IP.Builder().ip("127.0.0.1").requestDate(System.currentTimeMillis()).getCountry("DE").build());
		result.add(new IP.Builder().ip("128.0.0.1").requestDate(System.currentTimeMillis()).getCountry("DE").build());
		result.add(new IP.Builder().ip("129.0.0.1").requestDate(System.currentTimeMillis()).getCountry("DE").build());
		return result;
	}

}
