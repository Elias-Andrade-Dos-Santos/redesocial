package com.eliezer.br.redesocial.domain.DTO.publication.Comment;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public record CommentCreateDTO(UUID id, String content) {
    
}
