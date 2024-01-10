package com.eliezer.br.redesocial.domain.entitys.entityUserProfile;

import java.util.UUID;

import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserProfile  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private UUID id;

    private String profilePictureUrl;
    private String name;
    private int age;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public UserProfile(String profilePictureUrl, String name, int age, User user){
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
        this.age = age;
        this.user = user;
    }

    public UserProfile(UUID id2, String profilePictureUrl2, String name2, int age2) {
        this.id = id2;
        this.profilePictureUrl = profilePictureUrl2;
        this.name = name2;
        this.age = age2;
    }

    public UserProfile(String profilePictureUrl2, String name2, int age2) {
        this.profilePictureUrl = profilePictureUrl2;
        this.name = name2;
        this.age = age2;
    }

    public UserProfile(UUID id, String profilePictureUrl, String name) {
        this.id = id;
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
    }
}
