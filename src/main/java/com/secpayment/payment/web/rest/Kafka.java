package com.secpayment.payment.web.rest;

import com.secpayment.payment.domain.Payment;
import com.secpayment.payment.kafka.Producer;
import com.secpayment.payment.kafka.Storage;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Transactional(timeout = 60)
public class Kafka {

    private final Logger logger = LoggerFactory.getLogger(Kafka.class);

    @Autowired
    Producer producer;

    @Autowired
    private Storage messageStorage;

    Payment payment;

    @PostMapping("/kafka")
    public void sendMessage(@RequestBody Object pay) {
        producer.publishToTopic(pay);
    }

    public void getMessage(@RequestBody Payment pay) {
        this.payment = pay;
    }

    @GetMapping("/kafkadmin")
    public List<Payment> kafkaAdminMessage() {
        logger.info("Kafka-Admin : all message returnedFromService");
        return messageStorage.getAll();
    }

    @PostMapping("/kafkaPaymentApproval")
    public Payment paymentApproval(@RequestBody Payment pay) throws InterruptedException, ExecutionException, IOException {
        messageStorage.add(pay);
        logger.info("LIST OF PAYMENT STORED : {}", messageStorage.getAll());
        return pay;
    }
}
