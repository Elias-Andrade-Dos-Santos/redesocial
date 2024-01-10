package com.eliezer.br.redesocial.domain.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eliezer.br.redesocial.domain.DTO.PublicationDTO;
import com.eliezer.br.redesocial.domain.DTO.publication.PublicationUpdateDTO;
import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;
import com.eliezer.br.redesocial.domain.entitys.entityLike.Like;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.eliezer.br.redesocial.domain.repositories.PublicationRepository;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;



@Service
public class PublicationService {

    @Autowired
    private TokenJWT tokenJWT;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Transactional
    public ResponseEntity<Object> createPublication(PublicationDTO data){
        Optional<UserProfile> profile = userProfileRepository.findByUser(tokenJWT.getExtractedUser());
        if (profile.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("profile not found");
        }
        var newPublicacao = new Publication(UUID.randomUUID(),data.description(),data.videoUrl(),data.imageUrl(),LocalDateTime.now(),profile.get(),data.ativo());
        return ResponseEntity.status(HttpStatus.OK).body(publicationRepository.save(newPublicacao));
    }

    @Transactional
    public ResponseEntity<Object> getPublication(UUID id){
        System.out.println("Entrou aqui getPublication");
        Optional<Publication> publicationOptional = publicationRepository.findById(id);
        
        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(publicationOptional.get());
    }

    @Transactional
    public ResponseEntity<List<Publication>> getAllPublication(){
        System.out.println("getAllPublication");
        Optional<List<Publication>> publications = publicationRepository.findAllByUserProfileOrderByCreationDateDesc(tokenJWT.getExtractedUser());
        if (publications.isEmpty()) {
            return null;
        }

        List<Publication> publicationEntities = publications.get();
        publicationEntities.forEach(publication -> {
            List<Like> updatedLikes = publication.getLikes().stream()
                    .map(like -> {
                        Like likeEntity = new Like();
                        likeEntity.setCreationDate(like.getCreationDate());
                        likeEntity.setUserProfile(new UserProfile(like.getUserProfile().getId(),like.getUserProfile().getProfilePictureUrl(),like.getUserProfile().getName()));
                        return likeEntity;
                    })
                    .collect(Collectors.toList());
            publication.setLikes(updatedLikes);
        });
        publicationEntities.forEach(publication -> {
            List<Comment> updatedLikess = publication.getComments().stream()
                    .map(like -> {
                        Comment likeEntity = new Comment();
                        likeEntity.setCreationDate(like.getCreationDate());
                        likeEntity.setUserProfile(new UserProfile(like.getUserProfile().getId(),like.getUserProfile().getProfilePictureUrl(),like.getUserProfile().getName()));
                        return likeEntity;
                    })
                    .collect(Collectors.toList());
            publication.setComments(updatedLikess);
        });


    return ResponseEntity.status(HttpStatus.OK).body(publicationEntities);
    }

    @Transactional
    public ResponseEntity<Object> updatePublciation(UUID id, PublicationUpdateDTO data){
        Optional<Publication> existPublic = publicationRepository.findById(id);
         if (existPublic.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post not found");
        }
        
        Publication publi = existPublic.get(); 
        BeanUtils.copyProperties(data, publi);
        return ResponseEntity.status(HttpStatus.OK).body(publicationRepository.save(publi));
    }

    @Transactional
    public ResponseEntity<Object> deletePublciation(UUID id){
        Optional<Publication> existPublic = publicationRepository.findById(id);
         if (existPublic.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not  found");
        }
        publicationRepository.delete(existPublic.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
