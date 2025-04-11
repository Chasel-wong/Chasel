package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.Category;
import com.wqm.entity.query.CategoryQuery;
import com.wqm.entity.vo.CategoryListVo;
import com.wqm.entity.vo.PageVo;
import com.wqm.result.ResponseResult;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2024-10-26 08:56:56
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult listAllCategory();

    PageVo categoryList(String name, String status, Integer pageNum, Integer pageSize);

    void addCategory(CategoryQuery query);

    CategoryListVo getCatogoryById(Long id);

    void updateCategory(CategoryListVo vo);

    void deleteCategory(Long id);
}
