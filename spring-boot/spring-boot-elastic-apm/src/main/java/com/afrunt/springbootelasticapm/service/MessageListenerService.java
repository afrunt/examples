package com.afrunt.springbootelasticapm.service;

import co.elastic.apm.api.CaptureSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Andrii Frunt
 */
@Service
public class MessageListenerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListenerService.class);
    private final JmsTemplate jmsTemplate;

    public MessageListenerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "apmQueue")
    public void onMessage(TextMessage message) throws Exception {

        LOGGER.info("JMS message received");

        handleMessage(message);
    }

    @CaptureSpan
    private void handleMessage(TextMessage message) throws Exception {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));
        jmsTemplate.send(message.getJMSReplyTo(), session -> {
            Message responseMsg = session.createTextMessage("bar");
            responseMsg.setJMSCorrelationID(message.getJMSCorrelationID());
            return responseMsg;
        });
    }
}
