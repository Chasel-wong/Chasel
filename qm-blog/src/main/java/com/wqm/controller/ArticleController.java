package com.wqm.controller;

import com.wqm.entity.Article;
import com.wqm.result.ResponseResult;
import com.wqm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/list")
    public List<Article> test(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result =articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,  Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetil(@PathVariable Long id){
        return articleService.getArticleDetil(id);
    }
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
