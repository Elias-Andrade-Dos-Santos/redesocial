package com.eliezer.br.redesocial.Presentation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliezer.br.redesocial.domain.DTO.UserProfileDTO;
import com.eliezer.br.redesocial.domain.DTO.UserProfilePutDTO;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.domain.repositories.UserRepository;
import com.eliezer.br.redesocial.domain.services.UserProfileService;

import jakarta.validation.Valid;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("users")
public class UserProfileController {
    @Autowired
    UserProfileRepository profileRepository;
    @Autowired
    UserRepository repository;

    @Autowired
    UserProfileService profileService;

    @GetMapping
    public ResponseEntity<Object> getUserProfile() {
            return profileService.getUserProfile();
            
    }

    @PostMapping
    public ResponseEntity<Object> createUserProfile( @RequestBody @Valid UserProfileDTO data) {
            return profileService.createUserProfile(data);
            
    }
   
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUserProfile(@PathVariable UUID id, @RequestBody UserProfilePutDTO data) {
         return profileService.updateUserProfile(id ,data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserProfile(@PathVariable UUID id) {
        return profileService.deleteUserProfile(id);
    }
}
