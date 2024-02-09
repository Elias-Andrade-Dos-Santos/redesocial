package com.eliezer.br.redesocial.Presentation;

import org.springframework.web.bind.annotation.RestController;

import com.eliezer.br.redesocial.domain.DTO.publication.Comment.CommentCreateDTO;
import com.eliezer.br.redesocial.domain.DTO.publication.Comment.CommentUpdateDTO;
import com.eliezer.br.redesocial.domain.services.CommentService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Object> postMethodName(@RequestBody CommentCreateDTO data) {
        return commentService.createComment(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable UUID id, @RequestBody CommentUpdateDTO data) {
        return commentService.updateComment(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable UUID id) {
        return commentService.deleteComment(id);
    }
    
}
