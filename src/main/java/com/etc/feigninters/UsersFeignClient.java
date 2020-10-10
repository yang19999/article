package com.etc.feigninters;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;

/**
 * 定义一个feign接口，通过@FeignClient（“服务名”），来指定消费哪个服务。
 */
@FeignClient(name = "eureka-microservice-users",fallback = UserFeignClientFallback.class)  // 服务消费类要调用的服务类名
public interface UsersFeignClient {
    // values请求地址就是users用户服务中该控制器的接口地址
    @RequestMapping(value = "/user/get/{userId}",method = RequestMethod.GET)
    Map<String,Object> getUserById(@PathVariable("userId") Integer userId);

    @RequestMapping(value = "/user/get/{userId}",method = RequestMethod.GET)
    Map<String,Object> getArticleDetail(@PathVariable Integer articleId);
}
