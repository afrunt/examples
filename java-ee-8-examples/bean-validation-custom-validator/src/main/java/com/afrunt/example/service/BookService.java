package com.afrunt.example.service;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @author Andrii Frunt
 */
@Named
@Singleton
public class BookService {
    public boolean isValidBookName(String name) {
        return name == null || name.toLowerCase().contains("java ee 8");
    }
}
