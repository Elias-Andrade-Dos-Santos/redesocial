package com.eliezer.br.redesocial.domain.entitys.entityPublication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;
import com.eliezer.br.redesocial.domain.entitys.entityLike.Like;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="publication")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String description;
    private String arquivoUrl;
    private boolean ativo;
    
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "userProfile_id")
    @JsonIgnore
    private UserProfile userProfile;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Like> likes;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Comment> comments;


    public Publication(String description, String arquivoUrl,LocalDateTime creationDate,UserProfile userProfile, boolean ativo) {
            this.description = description;
            this.arquivoUrl = arquivoUrl;
            this.creationDate = creationDate;
            this.userProfile = userProfile;
            this.ativo = ativo;
    }


    public Publication(UUID randomUUID, String description, String arquivoUrl, LocalDateTime creationDate,
            UserProfile userProfile,  boolean ativo) {
            this.description = description;
            this.arquivoUrl = arquivoUrl;
            this.creationDate = creationDate;
            this.userProfile = userProfile;
            this.ativo = ativo;
        
    }


    public Publication(UUID id, String description, String arquivoUrl,LocalDateTime creationDate) {
        this.id = id;
        this.description = description;
        this.arquivoUrl = arquivoUrl;
        this.creationDate = creationDate;
        
        //TODO Auto-generated constructor stub

    }
}
