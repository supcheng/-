package com.atguigu.daijia.customer.service.impl;

import com.atguigu.daijia.common.constant.RedisConstant;
import com.atguigu.daijia.common.execption.GuiguException;
import com.atguigu.daijia.common.result.Result;
import com.atguigu.daijia.common.result.ResultCodeEnum;
import com.atguigu.daijia.customer.client.CustomerInfoFeignClient;
import com.atguigu.daijia.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerInfoFeignClient client;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public String login(String code) {
        Result<Long> loginResult = client.login(code);
        Long customerId = loginResult.getData();
        if (customerId == null) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_KEY_PREFIX + token, customerId.toString(),
                RedisConstant.USER_LOGIN_KEY_TIMEOUT, TimeUnit.SECONDS);
        return token;
    }
}
