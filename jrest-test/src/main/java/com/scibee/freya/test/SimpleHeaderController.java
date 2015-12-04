package com.scibee.freya.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scibee.freya.test.domain.UserGroup;

@RestController
@RequestMapping("/v1/header/simple")
public class SimpleHeaderController {

	@RequestMapping(method = RequestMethod.GET)
	public UserGroup GET(HttpServletRequest request) {
		final int userId = Integer.valueOf(request.getHeader("userId"));
		final int groupId = Integer.valueOf(request.getHeader("groupId"));
		return new UserGroup(userId, groupId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserGroup POST(HttpServletRequest request) {
		final int userId = Integer.valueOf(request.getHeader("userId"));
		final int groupId = Integer.valueOf(request.getHeader("groupId"));
		return new UserGroup(userId, groupId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserGroup PUT(HttpServletRequest request) {
		final int userId = Integer.valueOf(request.getHeader("userId"));
		final int groupId = Integer.valueOf(request.getHeader("groupId"));
		return new UserGroup(userId, groupId);
	}
}
