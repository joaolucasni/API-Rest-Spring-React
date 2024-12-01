package br.com.livro.controller;

import br.com.livro.mapper.AuthorMapper;
import br.com.livro.dto.AuthorDTO;
import br.com.livro.domain.model.Author;
import br.com.livro.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.net.URI;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    // Método GET para retornar todos os autores
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = authors.stream()
                                           .map(authorMapper::toDTO)
                                           .collect(Collectors.toList());
        return ResponseEntity.ok(authorDTOs);
    }

    // Método GET para retornar um autor por ID
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        return authorRepository.findById(id)
            .map(author -> ResponseEntity.ok(authorMapper.toDTO(author)))
            .orElse(ResponseEntity.notFound().build());
    }

    // Método POST para criar um autor
    @PostMapping
public ResponseEntity<AuthorDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
    Author author = authorMapper.toEntity(authorDTO); // Mapeia para entidade Author
    Author createdAuthor = authorRepository.save(author); // Salva o autor no banco de dados
    AuthorDTO createdAuthorDTO = authorMapper.toDTO(createdAuthor); // Mapeia o autor salvo de volta para DTO

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdAuthorDTO.getId())
        .toUri();

    return ResponseEntity.created(location).body(createdAuthorDTO); // Retorna o autor criado com status 201
}


    // Método PUT para atualizar um autor
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> updateAuthor(
        @PathVariable Long id, 
        @RequestBody AuthorDTO authorDTO
    ) {
        Author author = authorMapper.toEntity(authorDTO);
        author.setId(id);  // Garantir que o ID seja mantido na atualização
        Author updatedAuthor = authorRepository.save(author);
        AuthorDTO updatedAuthorDTO = authorMapper.toDTO(updatedAuthor);
        return ResponseEntity.ok(updatedAuthorDTO);
    }

    // Método DELETE para remover um autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
