package com.eliezer.br.redesocial.domain.DTO.publication;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;

public record PublicationDTOResponse(UUID id, String description, String arquivoUrl,LocalDateTime creationDate,List<Comment> comments) {
    
}
