package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogueResource {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private MyUserDetailsService desserv;

    @Autowired
    private JWTUtil util;

    @GetMapping(path = "/greet")
    public String greet() {
        return "Hello from the service!";
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<?> createJWtToken(@RequestBody AuthenticationRequest authReq) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.username, authReq.password));
        } catch (BadCredentialsException ex) {
            throw new Exception("Bad creds");
        }
        final UserDetails ud = desserv.loadUserByUsername(authReq.username);
        final String jwt = util.generateToken(ud);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
