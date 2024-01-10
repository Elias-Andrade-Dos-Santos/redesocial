package com.eliezer.br.redesocial.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eliezer.br.redesocial.domain.entitys.entityLike.Like;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

@Repository
public interface LikeRepository extends JpaRepository<Like, UUID>{
    Optional<Like> findByPublication(Publication post);
    Optional<Like> findByUserProfileAndPublication(UserProfile userProfile, Publication publication);
}
