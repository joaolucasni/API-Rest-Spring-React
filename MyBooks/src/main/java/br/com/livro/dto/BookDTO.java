package br.com.livro.dto;
public class BookDTO {
    private Long id;
    private String name;
    private AuthorDTO author;


    public BookDTO(Long id, String name, AuthorDTO author){
            this.id = id;
            this.name = name;
            this.author = author;
    }
    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
