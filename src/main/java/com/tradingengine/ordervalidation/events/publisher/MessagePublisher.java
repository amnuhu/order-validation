package com.tradingengine.ordervalidation.events.publisher;

import com.tradingengine.ordervalidation.dto.redis.RedisOrderDto;

public interface MessagePublisher {
     void publisher(RedisOrderDto order);
     void publishValidationSuccess(RedisOrderDto orderDto);
}
