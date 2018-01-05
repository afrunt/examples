package com.afrunt.example.validation.cdi;

import org.glassfish.jersey.server.validation.ValidationConfig;

import javax.enterprise.inject.spi.CDI;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Andrii Frunt
 */
@Provider
public class CDIProviderResolver implements ContextResolver<ValidationConfig> {
    @Context
    ResourceContext context;

    @Override
    public ValidationConfig getContext(Class<?> type) {
        return new ValidationConfig().constraintValidatorFactory(new CDIConstraintValidatorFactory(context));
    }

    public static class CDIConstraintValidatorFactory implements ConstraintValidatorFactory {
        private ResourceContext context;

        CDIConstraintValidatorFactory(ResourceContext context) {
            this.context = context;
        }

        @Override
        public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
            try {
                return CDI.current().select(key).get();
            } catch (Exception e) {
                return context.getResource(key);
            }
        }

        @Override
        public void releaseInstance(ConstraintValidator<?, ?> instance) {

        }
    }
}
