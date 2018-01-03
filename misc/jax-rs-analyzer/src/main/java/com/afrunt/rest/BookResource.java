package com.afrunt.rest;

import com.afrunt.model.Book;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Andrii Frunt
 */
@Path("/book")
@RequestScoped
public class BookResource {

    /**
     * Find book by identifier
     *
     * @param id book identifier
     * @return book
     */
    @GET
    @Path("/id/{id}")
    public Book findById(@PathParam("id") long id) {
        return new Book();
    }

    /**
     * Create new book
     *
     * @param book book object
     * @return book
     */
    @POST
    public Book create(Book book) {
        return book;
    }
}
