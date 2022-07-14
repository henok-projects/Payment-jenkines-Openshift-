package com.secpayment.payment.kafka;

import com.secpayment.payment.domain.Payment;
import com.secpayment.payment.web.rest.Kafka;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @Autowired
    Kafka kafka;

    private final Logger log = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(topics = "user-create", groupId = "user")
    public void receive(ConsumerRecord<String, LinkedHashMap<String, Object>> obj)
        throws IOException, ExecutionException, InterruptedException {
        log.info("Received / consumed object is : {}", obj);

        Payment pay = new Payment();

        pay.setCik(obj.value().get("cik").toString());
        pay.setCcc(obj.value().get("ccc").toString());
        pay.setPaymentAmout(obj.value().get("paymentAmout").toString());
        pay.setName((obj.value().get("name").toString()));
        pay.setEmail((obj.value().get("email").toString()));
        pay.setPhone((Integer) obj.value().get("phone"));
        pay.setApprovalStatus("");

        kafka.getMessage(pay);
        kafka.kafkaAdminMessage();
        kafka.paymentApproval(pay);
    }
}
