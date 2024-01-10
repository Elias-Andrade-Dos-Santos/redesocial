package com.eliezer.br.redesocial.domain.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    Optional<UserProfile> findByUser(User user);
}
