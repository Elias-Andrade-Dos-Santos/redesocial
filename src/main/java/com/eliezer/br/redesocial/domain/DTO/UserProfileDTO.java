package com.eliezer.br.redesocial.domain.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserProfileDTO(
        @NotBlank(message = "O URL da imagem do perfil é obrigatório.")
        String profilePictureUrl,
        @NotBlank(message = "O nome do usuario do perfil é obrigatório.")
        String name,
        @Min(value = 0, message = "A idade deve ser maior ou igual a 0.")
        int age) {
   
}
