package com.etc.controller;

import com.etc.entity.Article;
import com.etc.feigninters.UsersFeignClient;
import com.etc.service.ArticleService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private UsersFeignClient usersFeignClient;
    /**
     * 使用Feign调用服务类
     * @param articleId
     * @return
     */
    @RequestMapping("/getDetailByFeign/{articleId}")
    public Map<String,Object>  getArticleDetailByFeign(@PathVariable("articleId") Integer articleId, HttpSession session){
        Object user1 = session.getAttribute("user");
        System.out.println("从springzuul中取~~：" + user1);
        System.out.println("改动测试");
        // 获取文章信息
        Article article = articleService.getById(articleId);

        // 调用feign接口中定义的方法
        Map<String,Object> map = usersFeignClient.getUserById(article.getAuthorId());

        // 将数据放入map中
        map.put("articleId",article.getArticleId());
        map.put("articleTitle",article.getArticleTitle());
        map.put("articleContent",article.getArticleContent());
        map.put("articleDate",article.getArticleDate());

        return map;
    }


    //断路器的设置
    //从文章微服务访问用户微服务
//    @HystrixCommand(fallbackMethod = "getDefaultUser")
    @RequestMapping("/getuser/{uid}")
    public Map<String,Object> getUser(@PathVariable Integer uid){
//        Map<String,Object> map = restTemplate.getForObject("http://localhost:8762/user/get/"+uid,Map.class);
        Map<String, Object> map = usersFeignClient.getUserById(uid);
        return map;
    }
//    private Map<String,Object> getDefaultUser(Integer uid) {
//        System.out.println("熔断，默认回调函数");
//        Map<String, Object> map = new HashMap<>();
//        map.put("uname","-1");
//        map.put("password","-1");
//        return map;
//    }


    @RequestMapping("/get/{articleId}")
    public Article getArticle(@PathVariable Integer articleId, HttpSession session){
        Object user1 = session.getAttribute("user");
        System.out.println("从springzuul中取~~：" + user1);
        return articleService.getById(articleId);
    }

//    @RequestMapping("/getdetail/{articleId}")
//    public Map<String,Object> getArticleDetail(@PathVariable Integer articleId){
//        Article article = articleService.getById(articleId);
//        Map<String,Object> map = new HashMap<>();
//        if(article.getAuthorId() != null){//文章作者不为空
//            map = restTemplate.getForObject("http://localhost:8762/user/get/"+article.getAuthorId(),Map.class);
//        }
//        if(article != null){//把文章中的属性设置到map中
//            map.put("articleId",article.getArticleId());
//            map.put("articleTitle",article.getArticleTitle());
//            map.put("articleContent",article.getArticleContent());
//            map.put("articleDt",article.getArticleDate());
//        }
//        return map;
//    }
    @RequestMapping("/getdetail/{articleId}")
    public Map<String,Object> getArticleDetail(@PathVariable Integer articleId){
        Article article = articleService.getById(articleId);
        Map<String,Object> map = new HashMap<>();
        if(article.getAuthorId() != null){//文章作者不为空
            map = usersFeignClient.getArticleDetail(articleId);
        }
        if(article != null){//把文章中的属性设置到map中
            map.put("articleId",article.getArticleId());
            map.put("articleTitle",article.getArticleTitle());
            map.put("articleContent",article.getArticleContent());
            map.put("articleDt",article.getArticleDate());
        }
        return map;
    }
}