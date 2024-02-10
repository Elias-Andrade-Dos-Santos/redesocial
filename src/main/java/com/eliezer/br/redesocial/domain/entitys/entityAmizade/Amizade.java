package com.eliezer.br.redesocial.domain.entitys.entityAmizade;

import java.util.UUID;

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
@Table(name = "amizade")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Amizade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserProfile usuario;

    @ManyToOne
    @JoinColumn(name = "amigo_id")
    private UserProfile amigo;
}
