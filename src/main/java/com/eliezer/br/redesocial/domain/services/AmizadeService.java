package com.eliezer.br.redesocial.domain.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eliezer.br.redesocial.domain.entitys.entityAmizade.Amizade;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUser.User;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.eliezer.br.redesocial.domain.repositories.AmizadeRepository;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.domain.repositories.UserRepository;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;

import jakarta.transaction.Transactional;

@Service
public class AmizadeService {
    @Autowired
    private TokenJWT tokenJWT;

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UserProfileRepository profileRepository;

    @Autowired
    private UserRepository repository;


    @Transactional
    public ResponseEntity<Object> addAmizade(UUID id){

        Optional<User> Useramigo = repository.findById(id);
        if (Useramigo.isEmpty())return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario amigo não encontrado");

        Optional<UserProfile> amigo = profileRepository.findByUser(Useramigo.get());
        if (amigo.isEmpty())return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario amigo profile não encontrado");

        Optional<UserProfile> userProfile = profileRepository.findByUser(tokenJWT.getExtractedUser());
        if (userProfile.isEmpty())return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");

        var amizade = new  Amizade(UUID.randomUUID(), userProfile.get(), amigo.get());

        amizadeRepository.save(amizade);
        
        return ResponseEntity.ok().body("solicitação enviada");
    }

    @Transactional
    public ResponseEntity<List<Object>> buscarTodasAmizades() {
        Optional<UserProfile> userProfile = profileRepository.findByUser(tokenJWT.getExtractedUser());
        if (userProfile.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        Optional<List<Amizade>> amizades = amizadeRepository.findAllByUsuario(userProfile.get());
        if (amizades.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Object> listaDetalhesAmizades = criarListaDetalhesAmizades(amizades.get());
        return ResponseEntity.ok(listaDetalhesAmizades);
    }

    private List<Object> criarListaDetalhesAmizades(List<Amizade> amizades) {
        // Aqui você pode criar uma lista de objetos que contenham os detalhes de cada amizade
        // Por exemplo, você pode criar um objeto que contenha informações sobre o amigo associado a cada amizade
        // Este é um exemplo simplificado, você pode ajustá-lo de acordo com suas necessidades
        List<Object> detalhesAmizades = new ArrayList<>();
        for (Amizade amizade : amizades) {
            Map<String, Object> detalhes = new HashMap<>();
            detalhes.put("amigo_id", amizade.getAmigo().getId());
            detalhes.put("amigo_nome", amizade.getAmigo().getName());
            detalhes.put("amigo_foto", amizade.getAmigo().getProfilePictureUrl());
            // Adicione mais detalhes conforme necessário
            detalhesAmizades.add(detalhes);
        }
        return detalhesAmizades;
    }
}
