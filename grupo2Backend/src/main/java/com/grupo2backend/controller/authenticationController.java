package com.grupo2backend.controller;

import com.grupo2backend.dto.login;
import com.grupo2backend.dto.loginResponse;
import com.grupo2backend.entity.ClienteEntity;
import com.grupo2backend.services.AuthenticationService;
import com.grupo2backend.services.ClienteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.Cookie;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class authenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ClienteService clienteService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,Integer>> login(@RequestBody login log, HttpServletResponse response){
        loginResponse Response = authenticationService.Login(log);
        String token = Response.getToken();
        String cookieValue = "JWT=" + token + "; HttpOnly; Secure; SameSite=None; Path=/; Max-Age=" + (60 * 60 * 24);
        HashMap<String, Integer> message = new HashMap<>();
        response.addHeader("Set-Cookie", cookieValue);
        message.put("id_user", Response.getIdUser());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody ClienteEntity user) {
        return new ResponseEntity<>(authenticationService.Register(user), HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Boolean>> logout(HttpServletResponse response){
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);

        response.addCookie(jwtCookie);

        HashMap<String, Boolean> message = new HashMap<>();
        message.put("success", true);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Boolean>> verifyToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        authenticationService.verify(cookies);
        HashMap<String, Boolean> message = new HashMap<>();
        message.put("success", true);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
