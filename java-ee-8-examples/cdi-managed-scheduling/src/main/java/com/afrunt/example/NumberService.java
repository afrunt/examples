package com.afrunt.example;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * @author Andrii Frunt
 */
@Named
@ApplicationScoped
public class NumberService {
    private static final Logger LOGGER = Logger.getLogger(NumberService.class.getSimpleName());

    private int currentNumber = 0;

    public int getCurrentNumber() {
        return currentNumber;
    }

    @Scheduled(delay = 333)
    public void increment() {
        ++currentNumber;
        LOGGER.info("Current number incremented " + currentNumber);
    }

}
