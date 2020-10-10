package com.etc.service;

import com.etc.entity.Article;

public interface ArticleService {
    /*
     * 根据主键id获取文章对象
     * */
    Article getById(Integer articleId);
}
