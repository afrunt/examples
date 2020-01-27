package com.afrunt.springbootelasticapm;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.ElasticApm;
import com.afrunt.springbootelasticapm.repository.SampleEntityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Andrii Frunt
 */
@RestController
public class SampleController {
    private final SampleEntityRepository repository;
    private final JmsTemplate jmsTemplate;

    public SampleController(SampleEntityRepository repository, JmsTemplate jmsTemplate) {
        this.repository = repository;
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello() {
        darkSlowMethod1();
        repository.findAll();
        darkSlowMethod2();
        return ResponseEntity.ok(new HashMap<>());
    }

    @GetMapping("/hello-apm")
    public ResponseEntity<Map<String, Object>> helloApm() {
        ElasticApm.currentTransaction()
                // Labels are indexed
                .addLabel("INDEXED_LABEL", "VALUE")
                // Values from custom context are not indexed
                .addCustomContext("NON_INDEXED_LABEL", "VALUE");

        monitoredSlowMethod1(randomLong());
        repository.findAll();
        jmsTemplate.sendAndReceive("apmQueue", session -> session.createTextMessage("hello"));
        monitoredSlowMethod2(randomLong());
        return ResponseEntity.ok(new HashMap<>());
    }

    private void darkSlowMethod1() {
        sleep(1);
        nestedDarkSlowMethod();
    }

    private void darkSlowMethod2() {
        sleep(2);
    }

    private void nestedDarkSlowMethod() {
        sleep(1);
    }

    @CaptureSpan
    private void monitoredSlowMethod1(long inputParameter) {
        sleep(1);
        // Labels for current span only
        ElasticApm.currentSpan()
                .addLabel("SLEEP", 1)
                .addLabel("inputParameter", inputParameter);
        nestedMonitoredSlowMethod();
    }

    @CaptureSpan
    private void monitoredSlowMethod2(long inputParameter) {
        sleep(2);
        // Labels for current span only
        ElasticApm.currentSpan()
                .addLabel("SLEEP", 2)
                .addLabel("inputParameter", inputParameter);
    }

    @CaptureSpan
    private void nestedMonitoredSlowMethod() {
        sleep(1);
        // Labels for current span only
        ElasticApm.currentSpan()
                .addLabel("SLEEP", 1);
    }

    private long randomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    private void sleep(long seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
