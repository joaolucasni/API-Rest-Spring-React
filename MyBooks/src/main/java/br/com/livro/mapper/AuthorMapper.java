package br.com.livro.mapper;

import br.com.livro.dto.AuthorDTO;
import br.com.livro.domain.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    // Converter Author para AuthorDTO
    public AuthorDTO toDTO(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDTO(author.getId(), author.getName());
    }

    // Converter AuthorDTO para Author
    public Author toEntity(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            return null;
        }
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        return author;
    }
}
