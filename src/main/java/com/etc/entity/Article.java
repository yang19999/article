package com.etc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "article")
@Data
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;      //文章编号
    @Column(name = "article_title")
    private String articleTitle;    //文章标题
    @Column(name = "article_content")
    private String articleContent;  //文章内容
    @Column(name = "article_date")
    private String articleDate;     //发布时间

    @Column(name = "author_id") //关联的字段
    private Integer authorId;  //作者对象

}
