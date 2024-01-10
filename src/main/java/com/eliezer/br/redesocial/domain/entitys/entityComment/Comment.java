package com.eliezer.br.redesocial.domain.entitys.entityComment;

import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String content;

    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    @JsonBackReference
    private Publication publication;

  

    public Comment(String content, LocalDateTime creationDate, UserProfile userProfile, Publication publication) {
        this.content = content;
        this.creationDate = creationDate;
        this.userProfile = userProfile;
        this.publication = publication;
    }



    public Comment(String content) {
        this.content = content;
    }
}
