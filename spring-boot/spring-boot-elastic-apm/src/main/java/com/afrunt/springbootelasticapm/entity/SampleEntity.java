package com.afrunt.springbootelasticapm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Andrii Frunt
 */
@Entity
public class SampleEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String data;

    public Long getId() {
        return id;
    }

    public SampleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getData() {
        return data;
    }

    public SampleEntity setData(String data) {
        this.data = data;
        return this;
    }
}
