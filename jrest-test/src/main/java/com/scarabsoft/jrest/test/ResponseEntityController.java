package com.scarabsoft.jrest.test;

import com.google.gson.Gson;
import com.scarabsoft.jrest.test.domain.UserGroup;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/v1/response")
public class ResponseEntityController {

    @RequestMapping(method = RequestMethod.GET)
    public void GET(@RequestParam("userId") int userId, @RequestParam("groupId") int groupId, HttpServletResponse res)
            throws IOException {
        handle(userId, groupId, res);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void POST(@RequestParam("userId") int userId, @RequestParam("groupId") int groupId, HttpServletResponse res)
            throws IOException {
        handle(userId, groupId, res);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void PUT(@RequestParam("userId") int userId, @RequestParam("groupId") int groupId, HttpServletResponse res)
            throws IOException {
        handle(userId, groupId, res);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void DELETE(HttpServletResponse res)
            throws IOException {
        handle(1, 1, res);
    }


    private void handle(int userId, int groupId, HttpServletResponse res) throws IOException {
        final UserGroup resul = new UserGroup(userId, groupId);
        res.getWriter().write(new Gson().toJson(resul));
        res.addHeader("userId", String.valueOf(userId));
        res.addHeader("groupId", String.valueOf(groupId));
        res.addHeader("Cookie", "cookie");
        res.getWriter().flush();
    }

}
