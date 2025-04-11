package com.wqm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.config.RedisCache;
import com.wqm.domain.AddArticleDto;
import com.wqm.entity.*;
import com.wqm.entity.query.ArticleQuery;
import com.wqm.entity.vo.*;
import com.wqm.mapper.ArticleMapper;
import com.wqm.result.ResponseResult;
import com.wqm.service.ArticleService;
import com.wqm.service.ArticleTagService;
import com.wqm.service.CategoryService;
import com.wqm.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询文章
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //文章是否发布
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量降序
        lambdaQueryWrapper.orderByDesc(Article::getViewCount);
        //最多10条
        Page page = new Page(1,10);
        page(page,lambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        for (Article article:articles){
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());

        }
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);

    }

    @Override
    public ResponseResult getArticleDetil(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转化成vo
        ArticleDetailVo vo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类
        Long categoryId = vo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            vo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);
        List<ArticleTag> articleTags = new ArrayList<>();
        for (Long tagId:addArticleDto.getTags()){
            ArticleTag articleTag = new ArticleTag(article.getId(),tagId);
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
        
    }

    @Override
    public ResponseResult articleListVo(String title,String summary,int pageNum,int pageSize) {
        //查询文章数量
        Long num = articleMapper.selectBackArticleCount(title,summary);
        if (num == 0){
            return ResponseResult.okResult(new PageVo());
        }
//        //查询文章信息
//        List<Article> articles = articleMapper.selectBackArticleList(title,summary);
//        List<ArticleBackListVo> articleBackListVos = BeanCopyUtils.copyBeanList(articles, ArticleBackListVo.class);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getDelFlag,0)
                    .like(StringUtils.hasText(title),Article::getTitle,title)
                    .like(StringUtils.hasText(summary),Article::getSummary,summary);
        Page<Article> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNum);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        //上传数据
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult articleQueryByList(Long id) {
        Article article = baseMapper.selectById(id);
        return ResponseResult.okResult(article);
    }

    @Override
    public ResponseResult updateArticle(Article article) {
        baseMapper.updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }
}
