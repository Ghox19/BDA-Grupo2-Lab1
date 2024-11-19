package com.grupo2backend.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.grupo2backend.JWT.JWTUtil;
import com.grupo2backend.dto.login;
import com.grupo2backend.dto.loginResponse;
import com.grupo2backend.entity.ClienteEntity;
import com.grupo2backend.repository.ClienteRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    ClienteRepository clienteRepository;

    public loginResponse Login(login log){
        ClienteEntity clienteEntity = clienteRepository.findByEmail(log.getEmail());
        if (clienteEntity == null){
            throw new IllegalStateException("The name or password is incorrect");
        }
        if (!log.getClave().equals(clienteEntity.getClave())){
            throw new IllegalStateException("The name or password is incorrect");
        }

        return loginResponse.builder()
                .token(JWTUtil.createToken(log.getEmail()))
                .idUser(clienteEntity.getId_cliente().intValue())
                .build();
    }

    public ResponseEntity<Object> Register(ClienteEntity cliente){
        try{
            clienteRepository.save(cliente);
            return new ResponseEntity<>("se ingreso correctamente el cliente", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Error al ingresar el cliente" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void verify(Cookie[] cookies){
        if (cookies != null){
            for (Cookie cookie: cookies){
                if (cookie.getName().equals("JWT")){
                    try{
                        String token = cookie.getValue();
                        DecodedJWT decodedJWT = JWTUtil.verifyToken(token);
                        return;
                    } catch (JWTVerificationException e){
                        throw new IllegalStateException("JWT invalid");
                    }

                }
            }
            throw new IllegalStateException("JWT not found");
        }
        throw new IllegalStateException("cookies not found");
    }
}
