package com.scarabsoft.jrest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/auth/oauth2")
public class HttpOAuth2Controller {

    @RequestMapping(method = RequestMethod.GET)
    public String GET(HttpServletRequest request) {
        return handle(request);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String POST(HttpServletRequest request) {
        return handle(request);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String PUT(HttpServletRequest request) {
        return handle(request);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String DELETE(HttpServletRequest request) {
        return handle(request);
    }

    String handle(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
