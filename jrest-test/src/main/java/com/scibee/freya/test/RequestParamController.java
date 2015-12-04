package com.scibee.freya.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scibee.freya.test.domain.UserGroup;

@RestController
@RequestMapping("/v1/param")
public class RequestParamController {

	@RequestMapping(method = RequestMethod.GET)
	public UserGroup GET(@RequestParam(value = "userId") int userId, @RequestParam(value = "groupId") int groupId) {
		return new UserGroup(userId, groupId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserGroup POST(@RequestParam(value = "userId") int userId, @RequestParam(value = "groupId") int groupId) {
		return new UserGroup(userId, groupId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserGroup PUT(@RequestParam(value = "userId") int userId, @RequestParam(value = "groupId") int groupId) {
		return new UserGroup(userId, groupId);
	}

}
