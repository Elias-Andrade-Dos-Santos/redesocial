package com.eliezer.br.redesocial.domain.DTO.amizade;

import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

public record AmizadeAddDTO(UserProfile usuario, UserProfile amigo) {
    
}
