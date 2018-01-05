package com.afrunt.example.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * @author Andrii Frunt
 */
@Named
@ApplicationScoped
public class BookService {
    public boolean isValidBookName(String name) {
        return name == null || name.toLowerCase().contains("java ee 8");
    }
}
