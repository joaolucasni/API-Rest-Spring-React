package br.com.livro.service;

import br.com.livro.domain.model.Author;
import br.com.livro.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found"));
        
        author.setName(authorDetails.getName());
        
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Author not found"));
        
        authorRepository.delete(author);
    }
}
