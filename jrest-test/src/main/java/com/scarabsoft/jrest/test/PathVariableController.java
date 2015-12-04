package com.scarabsoft.jrest.test;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scarabsoft.jrest.test.domain.UserGroup;

@RestController
@RequestMapping("/v1/path/{userId}/{groupId}")
public class PathVariableController {

	@RequestMapping(method = RequestMethod.GET)
	public UserGroup GET(@PathVariable("userId") int userId, @PathVariable("groupId") int groupId) {
		return new UserGroup(userId, groupId);
	}

	@RequestMapping(method = RequestMethod.POST)
	public UserGroup POST(@PathVariable("userId") int userId, @PathVariable("groupId") int groupId) {
		return new UserGroup(userId, groupId);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public UserGroup PUT(@PathVariable("userId") int userId, @PathVariable("groupId") int groupId) {
		return new UserGroup(userId, groupId);
	}

}
