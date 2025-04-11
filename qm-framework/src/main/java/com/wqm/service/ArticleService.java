package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.Article;
import com.wqm.domain.AddArticleDto;
import com.wqm.entity.query.ArticleQuery;
import com.wqm.result.ResponseResult;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetil(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto articleVo);

    ResponseResult articleListVo(String title,String summary,int pageNum,int pageSize);

    ResponseResult articleQueryByList(Long id);

    ResponseResult updateArticle(Article article);

    ResponseResult deleteArticle(Long id);
}
