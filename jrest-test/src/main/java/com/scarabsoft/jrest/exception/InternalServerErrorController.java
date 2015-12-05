package com.scarabsoft.jrest.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/error/500")
public class InternalServerErrorController {

    @RequestMapping(method = RequestMethod.GET)
    public String GET() {
        throw new RuntimeException("test");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String POST() {
        throw new RuntimeException("test");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String PUT() {
        throw new RuntimeException("test");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void DELETE() {
        throw new RuntimeException("test");
    }

}
