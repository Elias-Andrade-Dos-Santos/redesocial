package com.eliezer.br.redesocial.Presentation;

import org.springframework.web.bind.annotation.RestController;

import com.eliezer.br.redesocial.domain.DTO.amizade.AmizadeControllerDTO;
import com.eliezer.br.redesocial.domain.entitys.entityAmizade.Amizade;
import com.eliezer.br.redesocial.domain.services.AmizadeService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("amizade")
public class AmizadeController {
    @Autowired
    private AmizadeService amizadeService;

    @PostMapping
    public ResponseEntity<Object> addAmizade(@RequestBody AmizadeControllerDTO data) {
        System.out.println(data);
       
        return amizadeService.addAmizade(data.amigoAdd());
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllAmizade() {
        return amizadeService.buscarTodasAmizades();
    }
    
}
