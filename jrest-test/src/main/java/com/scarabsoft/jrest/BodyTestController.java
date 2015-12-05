package com.scarabsoft.jrest;

import com.scarabsoft.jrest.domain.IP;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/body")
public class BodyTestController {

	@RequestMapping(method = RequestMethod.GET)
	public IP GET(@RequestBody IP ip) {
		return ip;
	}

	@RequestMapping(method = RequestMethod.POST)
	public IP POST(@RequestBody IP ip) {
		return ip;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void PUT(@RequestBody IP ip) {
	}

}
