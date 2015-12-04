package com.scibee.freya.test;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scibee.freya.interceptor.domain.HttpApplicationCredentials;

@RestController
@RequestMapping("/v1/auth/basic")
public class HttpBasicController {

	@RequestMapping(method = RequestMethod.GET)
	public HttpApplicationCredentials GET(HttpServletRequest request) {
		return handle(request);
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpApplicationCredentials POST(HttpServletRequest request) {
		return handle(request);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public HttpApplicationCredentials PUT(HttpServletRequest request) {
		return handle(request);
	}

	HttpApplicationCredentials handle(HttpServletRequest request) {
		final String header = request.getHeader("Authorization");
		final String[] arr = new String(Base64.decodeBase64(header.split(" ")[1]), Charset.forName("UTF-8"))
				.split(":");
		return new HttpApplicationCredentials(arr[0], arr[1]);
	}

}
