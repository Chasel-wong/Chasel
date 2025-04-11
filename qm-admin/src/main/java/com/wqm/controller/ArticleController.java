package com.wqm.controller;

import com.wqm.domain.AddArticleDto;
import com.wqm.entity.Article;
import com.wqm.entity.query.ArticleQuery;
import com.wqm.result.ResponseResult;
import com.wqm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/content")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("article")
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto){
        return articleService.add(addArticleDto);
    }
    @GetMapping("/article/list")
    public ResponseResult  articleListVo(@RequestParam(required = false) String title,@RequestParam(required = false) String summary,@RequestParam int pageNum,@RequestParam int pageSize){
        return articleService.articleListVo(title,summary,pageNum,pageSize);
    }
    @GetMapping("/article/{id}")
    public ResponseResult articleQueryByList(@PathVariable("id") Long id){
        return ResponseResult.okResult(articleService.articleQueryByList(id));
    }
    @PutMapping("article")
    public ResponseResult updateArticle(@RequestBody Article article){
        return ResponseResult.okResult(articleService.updateArticle(article));
    }
    @DeleteMapping("/article/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return ResponseResult.okResult(articleService.deleteArticle(id));
    }
}
