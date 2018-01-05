package com.afrunt.example.model;

import com.afrunt.example.validation.JavaEE8Book;

import javax.validation.constraints.NotNull;

/**
 * @author Andrii Frunt
 */
public class Book {
    @NotNull
    @JavaEE8Book
    private String name;

    public String getName() {
        return name;
    }

    public Book setName(String name) {
        this.name = name;
        return this;
    }
}
