package com.wqm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.entity.ArticleTag;
import com.wqm.mapper.ArticleTagMapper;
import com.wqm.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
