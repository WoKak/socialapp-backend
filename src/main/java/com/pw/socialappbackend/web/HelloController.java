package com.pw.socialappbackend.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/app")
public class HelloController {

    @GET
    @RequestMapping("/hello")
    public Response hello() {
        return Response.ok().build();
    }
}
