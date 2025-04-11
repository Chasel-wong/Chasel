package com.wqm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.entity.Article;
import com.wqm.entity.Category;
import com.wqm.entity.SystemConstants;
import com.wqm.entity.query.CategoryQuery;
import com.wqm.entity.vo.CategoryListVo;
import com.wqm.entity.vo.CategoryVo;
import com.wqm.entity.vo.PageVo;
import com.wqm.mapper.CategoryMapper;
import com.wqm.result.ResponseResult;
import com.wqm.service.ArticleService;
import com.wqm.service.CategoryService;
import com.wqm.utils.BeanCopyUtils;
import com.wqm.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-10-26 08:57:02
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        LambdaQueryWrapper<Article> articlewrapper = new LambdaQueryWrapper();
        articlewrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articlewrapper);
        //获取文章的分类id，并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().
                filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult listAllCategory() {
        List<Category> list = list();
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(list, CategoryListVo.class);
        return ResponseResult.okResult(categoryListVos);
    }

    @Override
    public PageVo categoryList(String name, String status, Integer pageNum, Integer pageSize) {
      LambdaQueryWrapper queryWrapper =     new LambdaQueryWrapper<Category>().eq(Category::getDelFlag,0)
                                          .like(StringUtils.hasText(name),Category::getName,name)
                                          .like(StringUtils.hasText(status),Category::getStatus,status);
        Page page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        return new PageVo(page.getRecords(),page.getTotal());
    }

    @Override
    public void addCategory(CategoryQuery query) {
        Category category = BeanCopyUtils.copyBean(query, Category.class);
        baseMapper.insert(category);
    }

    @Override
    public CategoryListVo getCatogoryById(Long id) {
        Category category = baseMapper.selectById(id);
        CategoryListVo categoryListVo = BeanCopyUtils.copyBean(category, CategoryListVo.class);
        return categoryListVo;
    }

    @Override
    public void updateCategory(CategoryListVo vo) {
        Category category = BeanCopyUtils.copyBean(vo, Category.class);
        baseMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Long id) {
        baseMapper.deleteById(id);
    }
}
