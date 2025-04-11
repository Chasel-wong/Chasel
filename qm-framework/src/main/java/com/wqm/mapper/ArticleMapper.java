package com.wqm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wqm.entity.Article;
import com.wqm.entity.query.ArticleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper extends BaseMapper<Article> {
    Long selectBackArticleCount(@Param("title")String title,@Param("summary")String summary);
    List<Article> selectBackArticleList(@Param("title")String title,@Param("summary")String summary);
}
