package com.secpayment.payment.kafka;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final Logger log = LoggerFactory.getLogger(Producer.class);

    public static final String TOPIC = "user-create";

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemp;

    public void publishToTopic(Object pay) {
        log.info("Publishing to topic : {}", TOPIC);
        this.kafkaTemp.send(TOPIC, pay);
    }
}
