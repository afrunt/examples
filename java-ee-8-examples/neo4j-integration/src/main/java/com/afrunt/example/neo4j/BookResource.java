package com.afrunt.example.neo4j;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Andrii Frunt
 */
@Path("/book")
@RequestScoped
public class BookResource {
    @Inject
    BookService bookService;

    @GET
    @Path("/id/{id}")
    public Book findById(@PathParam("id") long id) {
        return bookService.find(id).orElseThrow(() -> new WebApplicationException(Response.noContent().status(404).build()));
    }

    @POST
    public Book create(Book book) {
        return bookService.create(book);
    }

    @GET
    @Path("all")
    public List<Book> findAll(){
        return bookService.findAll();
    }
}
