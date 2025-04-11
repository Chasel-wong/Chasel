package com.wqm.job;

import com.wqm.config.RedisCache;
import com.wqm.entity.Article;
import com.wqm.service.ArticleService;
import com.wqm.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/50 * * * * ?")
    public void updateViewCount() {

        if (SecurityUtils.getLoginUser() == null) {
            return;  // 如果没有用户登录，直接跳过任务
        }


        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中

        articleService.updateBatchById(articles);
    }
}
