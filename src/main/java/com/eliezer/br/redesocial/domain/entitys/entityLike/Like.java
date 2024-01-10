package com.eliezer.br.redesocial.domain.entitys.entityLike;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.LastModifiedDate;

import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Like {
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    @JsonBackReference
    private Publication publication;

    public Like(LocalDateTime creationDate, UserProfile userProfile, Publication publication) {
        this.creationDate = creationDate;
        this.userProfile = userProfile;
        this.publication = publication;
    }
}
