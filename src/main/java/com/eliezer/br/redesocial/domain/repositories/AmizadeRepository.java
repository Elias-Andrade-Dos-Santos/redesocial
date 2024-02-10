package com.eliezer.br.redesocial.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eliezer.br.redesocial.domain.entitys.entityAmizade.Amizade;
import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

public interface AmizadeRepository extends JpaRepository<Amizade, UUID>{
    Optional<List<Amizade>> findAllByUsuario(UserProfile usuario);
}
