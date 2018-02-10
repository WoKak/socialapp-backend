package com.pw.socialappbackend.web;

import com.pw.socialappbackend.model.Tweet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/main")
public class MainpageController {

    @GET
    @RequestMapping("/message")
    public Response hello() {

        return Response.status(200)
                .entity(new Tweet("Hello World!"))
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
                .header("Access-Control-Allow-Headers", "X-Requested-With, Content-Type")
                .build();
    }
}
