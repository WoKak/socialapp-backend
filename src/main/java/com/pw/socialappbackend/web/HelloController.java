package com.pw.socialappbackend.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/app")
public class HelloController {

    @GET
    @RequestMapping("/hello")
    public Response hello() {
        return Response.status(200)
                .entity("Hello World!")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
                .header("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia,Authorization")
                .build();
    }
}
