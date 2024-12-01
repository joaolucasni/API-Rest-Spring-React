package br.com.livro.controller;

import br.com.livro.domain.model.Book;
import br.com.livro.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.stream.Collectors;

import java.net.URI;
import java.util.List;

import br.com.livro.dto.BookDTO;
import br.com.livro.mapper.BookMapper;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
    
    @Autowired
    private BookService bookService;

    @Autowired
    private BookMapper bookMapper;

    // Método GET para retornar todos os livros
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDTO> bookDTOs = books.stream()
                                      .map(bookMapper::toDTO)
                                      .collect(Collectors.toList());
        return ResponseEntity.ok(bookDTOs);
    }

    // Método GET para retornar um livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
            .map(book -> ResponseEntity.ok(bookMapper.toDTO(book)))
            .orElse(ResponseEntity.notFound().build());
    }

    // Método POST para criar um livro
    @PostMapping
public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
    Book book = bookMapper.toEntity(bookDTO);
    Book createdBook = bookService.createBook(book);
    BookDTO createdBookDTO = bookMapper.toDTO(createdBook);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdBookDTO.getId())
        .toUri();

    return ResponseEntity.created(location).body(createdBookDTO);
}


    // Método PUT para atualizar um livro
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
        @PathVariable Long id, 
        @Valid @RequestBody BookDTO bookDTO
    ) {
        Book book = bookMapper.toEntity(bookDTO);
        Book updatedBook = bookService.updateBook(id, book);
        BookDTO updatedBookDTO = bookMapper.toDTO(updatedBook);
        return ResponseEntity.ok(updatedBookDTO);
    }

    // Método DELETE para remover um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
