package com.afrunt.example.rest;

import com.afrunt.example.model.Book;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Andrii Frunt
 */
@Path("/book")
@Named
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @POST
    public Book create(@Valid Book book) {
        return book;
    }
}
