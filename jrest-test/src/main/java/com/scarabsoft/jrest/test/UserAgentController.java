package com.scarabsoft.jrest.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/useragent")
public class UserAgentController {

    @RequestMapping(method = RequestMethod.GET)
    public void GET(HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("user-agent", request.getHeader("user-agent"));
    }

}
