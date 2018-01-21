package com.afrunt.example;

import java.lang.annotation.*;

/**
 * @author Andrii Frunt
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Scheduled {
    long delay() default 1000;

    long initialDelay() default 0;
}