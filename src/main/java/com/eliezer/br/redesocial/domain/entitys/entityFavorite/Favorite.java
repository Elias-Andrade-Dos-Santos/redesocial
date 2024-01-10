package com.eliezer.br.redesocial.domain.entitys.entityFavorite;

import java.util.UUID;

import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "favorite")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;
}
