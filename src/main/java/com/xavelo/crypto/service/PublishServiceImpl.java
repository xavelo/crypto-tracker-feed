package com.xavelo.crypto.service;

import com.xavelo.crypto.Price;
import com.xavelo.crypto.adapter.KafkaAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishServiceImpl implements PublishService {

    @Autowired
    KafkaAdapter kafkaAdapter;

    @Override
    public void publishPrice(Price price) {
        kafkaAdapter.produceMessage("test-topic", "test");
    }
}
