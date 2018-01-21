package com.afrunt.example;

import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * @author Andrii Frunt
 */
public class SchedulingExtension implements Extension {
    private static final Logger LOGGER = Logger.getLogger(SchedulingExtension.class.getSimpleName());
    private static volatile boolean schedulerStarted = false;

    private final Map<Bean<?>, List<Method>> scheduledMethods = new HashMap<>();

    public void startScheduler(@Observes AfterDeploymentValidation event) {
        scheduleTasks();
        schedulerStarted = true;
        LOGGER.info("Scheduler started");
    }

    public void stopScheduler(@Observes BeforeShutdown event) {
        schedulerStarted = false;
        LOGGER.info("Scheduler stopped");
    }

    private void scheduleTasks() {
        scheduledMethods.forEach(this::scheduleTasks);
    }

    private void scheduleTasks(Bean<?> bean, List<Method> methods) {
        methods
                .forEach(m -> scheduleTask(bean, m));
    }

    private void scheduleTask(Bean<?> bean, Method method) {
        Scheduled annotation = method.getAnnotation(Scheduled.class);
        Runnable task = createTask(bean, method);
        getManagedScheduledExecutorService().scheduleWithFixedDelay(task, annotation.initialDelay(), annotation.delay(), TimeUnit.MILLISECONDS);
    }

    private Runnable createTask(Bean<?> bean, Method method) {
        return () -> {
            if (!schedulerStarted) {
                return;
            }
            try {
                method.invoke(CDI.current().select(bean.getBeanClass()).get());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void processScheduledMethods(@Observes ProcessBean<?> pb) {
        Bean<?> bean = pb.getBean();

        Arrays.stream(bean.getBeanClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Scheduled.class) && m.getParameterCount() == 0)
                .forEach(sm -> {
                    List<Method> methods = scheduledMethods.getOrDefault(bean, new ArrayList<>());
                    methods.add(sm);
                    scheduledMethods.put(bean, methods);
                });
    }

    private ManagedScheduledExecutorService getManagedScheduledExecutorService() {
        try {
            return InitialContext.doLookup("java:comp/DefaultManagedScheduledExecutorService");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
