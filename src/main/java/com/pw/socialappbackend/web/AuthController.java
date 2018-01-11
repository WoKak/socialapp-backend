package com.pw.socialappbackend.web;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GET
    @RequestMapping("/get-token")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getToken(@RequestBody String login) {

        HashFunction hf = Hashing.sha512();
        HashCode hc = hf.newHasher().putString(login, Charsets.UTF_8).hash();
        String token = hc.toString();

        return Response.status(200)
                .entity(token)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS")
                .header("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia,Authorization")
                .build();
    }
}
