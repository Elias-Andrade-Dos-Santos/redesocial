package com.eliezer.br.redesocial.Presentation;

import org.springframework.web.bind.annotation.RestController;

import com.eliezer.br.redesocial.domain.services.LikeService;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("like")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/{id}")
    public ResponseEntity<Object> postMethodName(@PathVariable UUID id) {
        return likeService.createLike(id);
    }
    
}
