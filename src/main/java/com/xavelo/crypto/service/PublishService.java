package com.xavelo.crypto.service;

import com.xavelo.crypto.Price;
import org.springframework.stereotype.Service;

@Service
public interface PublishService {

    public void publishPrice(Price price);

}
