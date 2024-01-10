package com.eliezer.br.redesocial.domain.DTO;

public record UserProfilePutDTO (
    String profilePictureUrl,
    String name,
    int age){
}
