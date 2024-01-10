package com.eliezer.br.redesocial.domain.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    Optional<Publication> findByUserProfile(UserProfile userProfile);
    Optional<List<Publication>> findAllByUserProfileOrderByCreationDateDesc(User user);
}
