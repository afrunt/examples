package com.afrunt.example.validation;

import com.afrunt.example.service.BookService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Andrii Frunt
 */
@Named
@Singleton
public class JavaEE8BookValidator implements ConstraintValidator<JavaEE8Book, String> {
    @Inject
    BookService bookService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return bookService.isValidBookName(name);
    }
}
