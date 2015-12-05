package com.scarabsoft.jrest;

import com.scarabsoft.jrest.domain.UserGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(method = RequestMethod.DELETE)
    public UserGroup DELETE(HttpServletRequest request) {
        final int userId = Integer.valueOf(request.getHeader("userId"));
        final int groupId = Integer.valueOf(request.getHeader("groupId"));
        return new UserGroup(userId, groupId);
    }
}
