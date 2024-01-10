package com.eliezer.br.redesocial.domain.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eliezer.br.redesocial.domain.entitys.entityLike.Like;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.eliezer.br.redesocial.domain.repositories.LikeRepository;
import com.eliezer.br.redesocial.domain.repositories.PublicationRepository;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;

import jakarta.transaction.Transactional;

@Service
public class LikeService {
    @Autowired
    private TokenJWT tokenJWT;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Transactional
    public ResponseEntity<Object> createLike(UUID id){
        Optional<Publication> post= publicationRepository.findById(id);
        if (post.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post not found");
        }
        Optional<UserProfile> profile = userProfileRepository.findByUser(tokenJWT.getExtractedUser());
        if (profile.isEmpty()) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("profile not found");
        }
        Optional<Like> likeExiste = likeRepository.findByUserProfileAndPublication(profile.get(),post.get());
        if (likeExiste.isPresent()) {

          likeRepository.delete(likeExiste.get());
          return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        var newLike = new Like(LocalDateTime.now(),profile.get(),post.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(likeRepository.save(newLike));
    }
    
}
