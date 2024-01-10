package com.eliezer.br.redesocial.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Optional<Comment> findByUserProfileAndPublication(UserProfile userProfile, Publication publication);
}
