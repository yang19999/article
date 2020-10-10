package com.etc.feigninters;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserFeignClientFallback implements UsersFeignClient {
    @Override
    public Map<String, Object> getUserById(Integer userId) {
        System.out.println("熔断，默认回调函数");
        Map<String, Object> map = new HashMap<>();
        map.put("uname","-1");
        map.put("password","-1");
        return map;
    }

    @Override
    public Map<String, Object> getArticleDetail(Integer articleId) {
        System.out.println("熔断，默认回调函数");
        Map<String, Object> map = new HashMap<>();
        map.put("uname","-1");
        map.put("password","-1");
        return map;
    }
}
