package com.eliezer.br.redesocial.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.eliezer.br.redesocial.domain.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> existUser = repository.findByEmail(username);
        if(existUser == null) return null;
        return existUser.get();
    }
    
}
