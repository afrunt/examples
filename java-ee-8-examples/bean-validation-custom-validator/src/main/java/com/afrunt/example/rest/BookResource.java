package com.afrunt.example.rest;

import com.afrunt.example.model.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @author Andrii Frunt
 */
@Path("/book")
@Named
@RequestScoped
public class BookResource {
    @POST
    public Book create(@Valid Book book) {
        return book;
    }
}
