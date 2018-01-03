package com.afrunt.model;

/**
 * @author Andrii Frunt
 */
public class Book {
    private Long id;
    private String name;
    private String isbn;

    /**
     * ID of the book
     * @return ID of the book
     */
    public Long getId() {
        return id;
    }

    public Book setId(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Name of the book
     * @return Name of the book
     */
    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }
}
