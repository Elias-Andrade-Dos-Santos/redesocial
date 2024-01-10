package com.eliezer.br.redesocial.domain.DTO.publication;

import java.util.UUID;

import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUser.User;

public record PublicationRespondeDTO(UUID id,User user,Publication publication) {
    
}
