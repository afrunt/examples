package com.afrunt.example.neo4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

/**
 * @author Andrii Frunt
 */
@Named
@ApplicationScoped
public class Resources {
    @Produces
    public Jsonb createJsonb(){
        return JsonbBuilder.create();
    }

}
