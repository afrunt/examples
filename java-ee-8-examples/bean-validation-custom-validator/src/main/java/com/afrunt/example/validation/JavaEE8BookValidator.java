package com.afrunt.example.validation;

import com.afrunt.example.service.BookService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Andrii Frunt
 */
@Named
@ApplicationScoped
public class JavaEE8BookValidator implements ConstraintValidator<JavaEE8Book, String> {
    @Inject
    BookService bookService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        //return CDI.current().select(BookService.class).get().isValidBookName(name);
        return bookService.isValidBookName(name);
    }
}
