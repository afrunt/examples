package com.afrunt.example.mapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * @author Andrii Frunt
 */
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("error", "Constraints violation");
        map.put("messages", exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));

        return Response.status(Response.Status.BAD_REQUEST).entity(map).build();
    }
}
