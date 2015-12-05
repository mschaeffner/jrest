package com.scarabsoft.jrest.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/v1/download")
public class DownloadTestController {

    @RequestMapping(method = RequestMethod.GET)
    public void GET(HttpServletResponse response) throws IOException {
        handle(response);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void POST(HttpServletResponse response) throws IOException {
        handle(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void PUT(HttpServletResponse response) throws IOException {
        handle(response);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void DELETE(HttpServletResponse response) throws IOException {
        handle(response);
    }

    private void handle(HttpServletResponse response) throws IOException {
        final byte[] data = new byte[10];
        for (int i = 0; i < 10; i++) {
            data[i] = 1;
        }
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=somefile.pdf");
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }

}
