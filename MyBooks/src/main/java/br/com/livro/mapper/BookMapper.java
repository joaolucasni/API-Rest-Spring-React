package br.com.livro.mapper;

import br.com.livro.domain.model.Book;
import br.com.livro.domain.model.Author;
import br.com.livro.dto.BookDTO;
import br.com.livro.dto.AuthorDTO;
import br.com.livro.domain.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    @Autowired
    private AuthorRepository authorRepository;  // Injetar o AuthorRepository

    // Converter Book para BookDTO
    public BookDTO toDTO(Book book) {
        if (book == null) {
            return null;
        }
        // Mapeia o autor para o AuthorDTO
        AuthorDTO authorDTO = null;
        if (book.getAuthor() != null) {
            authorDTO = new AuthorDTO(book.getAuthor().getId(), book.getAuthor().getName());  // Não precisamos de bookNames aqui
        }
        return new BookDTO(book.getId(), book.getName(), authorDTO);
    }

    // Converter BookDTO para Book
    public Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            return null;
        }
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setName(bookDTO.getName());

        // Lógica para associar o autor ao livro
        if (bookDTO.getAuthor() != null) {
            Author author = authorRepository.findById(bookDTO.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));
            book.setAuthor(author);  // Associa o autor ao livro
        }

        return book;
    }
}
