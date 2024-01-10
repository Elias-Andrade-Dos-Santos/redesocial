package com.eliezer.br.redesocial.Presentation;

import org.springframework.web.bind.annotation.RestController;

import com.eliezer.br.redesocial.domain.DTO.AuthDTO;
import com.eliezer.br.redesocial.domain.DTO.LoginResponseDTO;
import com.eliezer.br.redesocial.domain.DTO.RegisteDTO;
import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.eliezer.br.redesocial.domain.repositories.UserRepository;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("auth")
public class AuthenticationController {
     @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenJWT tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisteDTO data){
        if(this.repository.findByEmail(data.email()).isEmpty()) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.email(), encryptedPassword);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }
}
