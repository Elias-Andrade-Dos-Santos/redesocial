package com.eliezer.br.redesocial.domain.services;
import java.net.URLEncoder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.eliezer.br.redesocial.FileStorageProperties;
import com.eliezer.br.redesocial.domain.DTO.publication.PublicationDTOResponse;
import com.eliezer.br.redesocial.domain.DTO.publication.PublicationUpdateDTO;
import com.eliezer.br.redesocial.domain.entitys.entityComment.Comment;
import com.eliezer.br.redesocial.domain.entitys.entityLike.Like;
import com.eliezer.br.redesocial.domain.entitys.entityPublication.Publication;
import com.eliezer.br.redesocial.domain.entitys.entityUserProfile.UserProfile;
import com.eliezer.br.redesocial.domain.repositories.PublicationRepository;
import com.eliezer.br.redesocial.domain.repositories.UserProfileRepository;
import com.eliezer.br.redesocial.infrastructure.ServerConfig;
import com.eliezer.br.redesocial.infrastructure.security.TokenJWT;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class PublicationService {

    private final Path fileStorageLocation;

    @Autowired
    private TokenJWT tokenJWT;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public PublicationService(FileStorageProperties fileStorageProperties){
      this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    private final String DIRETORIO_UPLOAD = "src/main/resources/static/uploads";


    @Transactional
    public ResponseEntity<Object> createPublication(MultipartFile file,String description,boolean ativo){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String urlArquivo = salvarArquivo(file);

        try {
            Path targetLocation = fileStorageLocation.resolve(fileName);
            file.transferTo(targetLocation);

            

            Optional<UserProfile> profile = userProfileRepository.findByUser(tokenJWT.getExtractedUser());
            if (profile.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("profile not found");
            }

            var newPublicacao = new Publication(UUID.randomUUID(),description,urlArquivo,LocalDateTime.now(),profile.get(),ativo);
            publicationRepository.save(newPublicacao);
            return ResponseEntity.status(HttpStatus.OK).body(publicationRepository.save(newPublicacao));

        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        
    }

    @Transactional
    public ResponseEntity<Object> getPublication(UUID id, HttpServletRequest request) throws IOException {
        Optional<Publication> publicationOptional = publicationRepository.findById(id);

        if (publicationOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok().body(new PublicationDTOResponse(publicationOptional.get().getId(), publicationOptional.get().getDescription(), gerarUrlArquivo(publicationOptional.get().getArquivoUrl()),publicationOptional.get().getCreationDate(),publicationOptional.get().getComments()));

    }

    private String salvarArquivo(MultipartFile arquivo) {
        try {
            // Cria um nome único para o arquivo
            String nomeArquivo = UUID.randomUUID().toString() + "-" + arquivo.getOriginalFilename();
            // Define o caminho completo onde o arquivo será salvo
            Path caminhoCompleto = Paths.get(DIRETORIO_UPLOAD + File.separator + nomeArquivo);
            // Salva o arquivo no sistema de arquivos
            Files.copy(arquivo.getInputStream(), caminhoCompleto);
            // Retorna a URL completa do arquivo
            return "/uploads/" + nomeArquivo;
        } catch (IOException e) {
            e.printStackTrace();
            // Em caso de erro, retorna null ou lança uma exceção, dependendo do seu caso de uso
            return null;
        }
    }

    public String gerarUrlArquivo(String nomeArquivo) {
        try {
            String nomeArquivoFormatado = URLEncoder.encode(nomeArquivo, StandardCharsets.UTF_8.toString())
                    .replace("+", "%20") // Substitui "+" por "%20" para melhor legibilidade
                    .replace("%2F", "/"); // Substitui "%2F" de volta para "/"
            return "http://" + serverConfig.getServerHost() + ":" + serverConfig.getServerPort() +  nomeArquivoFormatado;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // Lidar com a exceção, se necessário
            return null;
        }
    }

    @Transactional
    public ResponseEntity<List<Publication>> getAllPublication(){
        System.out.println("getAllPublication");
        Optional<List<Publication>> publications = publicationRepository.findAllByUserProfileOrderByCreationDateDesc(tokenJWT.getExtractedUser());
        if (publications.isEmpty()) {
            return null;
        }

        List<Publication> publicationEntities = publications.get();
        publicationEntities.forEach(publication -> {
            List<Like> updatedLikes = publication.getLikes().stream()
                    .map(like -> {
                        Like likeEntity = new Like();
                        likeEntity.setCreationDate(like.getCreationDate());
                        likeEntity.setUserProfile(new UserProfile(like.getUserProfile().getId(),like.getUserProfile().getProfilePictureUrl(),like.getUserProfile().getName()));
                        return likeEntity;
                    })
                    .collect(Collectors.toList());
            publication.setLikes(updatedLikes);
        });
        publicationEntities.forEach(publication -> {
            List<Comment> updatedLikess = publication.getComments().stream()
                    .map(like -> {
                        Comment likeEntity = new Comment();
                        likeEntity.setCreationDate(like.getCreationDate());
                        likeEntity.setUserProfile(new UserProfile(like.getUserProfile().getId(),like.getUserProfile().getProfilePictureUrl(),like.getUserProfile().getName()));
                        return likeEntity;
                    })
                    .collect(Collectors.toList());
            publication.setComments(updatedLikess);
        });


    return ResponseEntity.status(HttpStatus.OK).body(publicationEntities);
    }

    @Transactional
    public ResponseEntity<Object> updatePublciation(UUID id, PublicationUpdateDTO data){
        Optional<Publication> existPublic = publicationRepository.findById(id);
         if (existPublic.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("post not found");
        }
        
        Publication publi = existPublic.get(); 
        BeanUtils.copyProperties(data, publi);
        return ResponseEntity.status(HttpStatus.OK).body(publicationRepository.save(publi));
    }

    @Transactional
    public ResponseEntity<Object> deletePublciation(UUID id){
        Optional<Publication> existPublic = publicationRepository.findById(id);
         if (existPublic.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not  found");
        }
        publicationRepository.delete(existPublic.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
