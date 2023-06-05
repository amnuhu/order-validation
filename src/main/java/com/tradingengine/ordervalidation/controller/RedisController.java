package com.tradingengine.ordervalidation.controller;

import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;
import com.tradingengine.ordervalidation.events.publisher.Publisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RedisController {

    @Autowired
    Publisher publisher;

    @RequestMapping(value = "/publisher", method = RequestMethod.POST)
    public void  publish(@RequestBody RedisOrderDto message) {
        log.info(">>>> Publishing:   {}", message);
        publisher.publisher(message);
    }
}
