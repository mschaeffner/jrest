package com.scarabsoft.jrest;

import com.scarabsoft.jrest.domain.UserGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/param")
public class RequestParamController {

    @RequestMapping(value = "/text",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    public String GET_STRING(@RequestParam("text") String text){
        return "{\"text\":\""+text+"\"}";
    }

    @RequestMapping(value = "/text",method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
    public String PUT_STRING(@RequestParam("text") String text){
        return "{\"text\":\""+text+"\"}";
    }

    @RequestMapping(value = "/text",method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String POST_STRING(@RequestParam("text") String text){
        return "{\"text\":\""+text+"\"}";
    }


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

    @RequestMapping(method = RequestMethod.DELETE)
    public UserGroup DELETE(@RequestParam(value = "userId") int userId, @RequestParam(value = "groupId") int groupId) {
        return new UserGroup(userId, groupId);
    }

}
