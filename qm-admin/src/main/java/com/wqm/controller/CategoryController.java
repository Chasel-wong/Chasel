package com.wqm.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.wqm.config.WebUtils;
import com.wqm.entity.Category;
import com.wqm.entity.query.CategoryQuery;
import com.wqm.entity.vo.CategoryListVo;
import com.wqm.entity.vo.ExcelCategoryVo;
import com.wqm.enums.AppHttpCodeEnum;
import com.wqm.result.ResponseResult;
import com.wqm.service.CategoryService;
import com.wqm.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws UnsupportedEncodingException {
        //设置请求头
        WebUtils.setDownLoadHeader("分类.xlsx",response);
        //获取导出数据
        List<Category> list = categoryService.list();
        List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(list, ExcelCategoryVo.class);
        //把数据写入excel
        try {
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("模板")
                    .doWrite(excelCategoryVos);
        } catch (IOException e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
    @GetMapping("list")
    public ResponseResult categoryList(@RequestParam(required = false)String name, @RequestParam(required = false)String status, Integer pageNum, Integer pageSize){
             return ResponseResult.okResult(categoryService.categoryList(name,status,pageNum,pageSize));
    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody  CategoryQuery query){
        categoryService.addCategory(query);
        return ResponseResult.okResult();
    }
    @GetMapping("{id}")
    public ResponseResult getCatogoryById(@PathVariable("id") Long id ){
        return ResponseResult.okResult(categoryService.getCatogoryById(id)) ;
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody CategoryListVo vo){
        categoryService.updateCategory(vo);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
        return ResponseResult.okResult();
    }
}
