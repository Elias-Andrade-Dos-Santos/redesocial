package com.eliezer.br.redesocial.domain.services;

import com.eliezer.br.redesocial.domain.DTO.UserProfilePutDTO;
import com.eliezer.br.redesocial.domain.DTO.profile.UserProfileDTO;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private TokenJWT tokenJWT;

    @Transactional
    public ResponseEntity<Object> getUserProfile( ) {
        Optional<UserProfile> existUserProfile = profileRepository.findByUser(tokenJWT.getExtractedUser());
        if (existUserProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile não encontrado");
        }

        UserProfile responseUserProfile = new UserProfile(existUserProfile.get().getId(), existUserProfile.get().getProfilePictureUrl(), existUserProfile.get().getName(), existUserProfile.get().getAge());
        return ResponseEntity.status(HttpStatus.OK).body(responseUserProfile);
    }

    @Transactional
    public ResponseEntity<Object> createUserProfile( UserProfileDTO data) {
        Optional<UserProfile> existUserProfile = profileRepository.findByUser(tokenJWT.getExtractedUser());
        if (!existUserProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile já cadastrado");
        }

        UserProfile userProfile = new UserProfile(data.profilePictureUrl(), data.name(), data.age(), tokenJWT.getExtractedUser());
        UserProfile savedUserProfile = profileRepository.save(userProfile);

        UserProfile responseUserProfile = new UserProfile(savedUserProfile.getId(), savedUserProfile.getProfilePictureUrl(), savedUserProfile.getName(), savedUserProfile.getAge());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserProfile);
    }

    @Transactional
    public ResponseEntity<Object> updateUserProfile(UUID id, UserProfilePutDTO data) {
        Optional<UserProfile> existUserProfile = profileRepository.findById(id);
        if (existUserProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile não existe");
        }

        UserProfile userProfile = existUserProfile.get();
        BeanUtils.copyProperties(data, userProfile);

        return ResponseEntity.status(HttpStatus.CREATED).body(profileRepository.save(userProfile));
    }

    @Transactional
    public ResponseEntity<Object> deleteUserProfile(UUID id) {
        Optional<UserProfile> existUserProfile = profileRepository.findById(id);
        if (existUserProfile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile não existe");
        }

        profileRepository.delete(existUserProfile.get());
        return ResponseEntity.status(HttpStatus.OK).body("Perfil excluído com sucesso.");
    }
}
