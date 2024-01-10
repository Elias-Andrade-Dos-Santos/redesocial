package com.eliezer.br.redesocial.domain.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eliezer.br.redesocial.domain.DTO.publication.Comment.CommentCreateDTO;
import com.eliezer.br.redesocial.domain.DTO.publication.Comment.CommentUpdateDTO;
import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.eliezer.br.redesocial.domain.repositories.CommentRepository;
import com.eliezer.br.redesocial.domain.repositories.PublicationRepository;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;

import jakarta.transaction.Transactional;

@Service
public class CommentService {
    @Autowired
    private TokenJWT tokenJWT;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    /**
     * @return
     */
    @Transactional
    public ResponseEntity<Object> createComment(CommentCreateDTO data){

      Optional<Publication> post= publicationRepository.findById(data.id());
      if (post.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("post not found");
      }
      System.out.println(post.get().getId());

      Optional<UserProfile> profile = userProfileRepository.findByUser(tokenJWT.getExtractedUser());
      if (profile.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("profile not found");
      }

      var newComment = new Comment(UUID.randomUUID(),data.content(),LocalDateTime.now(),profile.get(),post.get());
      
      return ResponseEntity.status(HttpStatus.CREATED).body(commentRepository.save(newComment));
    }

    @Transactional
    public ResponseEntity<Object> updateComment(UUID id,CommentUpdateDTO data){
      Optional<Comment> Comment = commentRepository.findById(id);
      if (Comment.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
      }

      Comment comme = Comment.get();
      BeanUtils.copyProperties(data, comme);
      return ResponseEntity.status(HttpStatus.OK).body(commentRepository.save(comme));
    }

    @Transactional
    public ResponseEntity<Object> deleteComment(UUID id){
      Optional<Comment> Comment = commentRepository.findById(id);
      if (Comment.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found");
      }

      commentRepository.delete(Comment.get());
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
