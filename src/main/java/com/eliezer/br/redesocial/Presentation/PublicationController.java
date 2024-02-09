package com.eliezer.br.redesocial.Presentation;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eliezer.br.redesocial.domain.DTO.publication.PublicationUpdateDTO;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.repositories.PublicationRepository;
import com.eliezer.br.redesocial.domain.services.PublicationService;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping("publication")
public class PublicationController {
    @Autowired
    PublicationRepository publicationRepository;

    @Autowired
    PublicationService publicationService;

    @PostMapping
    public ResponseEntity<Object> postMethodCreate(@RequestParam("file") MultipartFile file,  @RequestParam("description") String description, @RequestParam("ativo") boolean ativo) { 
        return publicationService.createPublication(file, description, ativo);
        // return ResponseEntity.status(HttpStatus.OK).body("publicationRepository.save(newPublicacao)");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMethodGet(@PathVariable UUID id, HttpServletRequest request) throws IOException {
        return publicationService.getPublication(id, request);
        // return ResponseEntity.status(HttpStatus.OK).body("publicationRepository.save(newPublicacao)");
    }

    @GetMapping("/allPublication")
    public ResponseEntity<List<Publication>> getAllMethodNameget() {
        return publicationService.getAllPublication();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodupdate(@PathVariable UUID id, @RequestBody PublicationUpdateDTO data) {
        return publicationService.updatePublciation(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable UUID id){
        return publicationService.deletePublciation(id);
    }
    
}
