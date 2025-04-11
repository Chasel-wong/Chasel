package com.wqm.controller;

import com.wqm.entity.query.MenuQuery;
import com.wqm.entity.request.MenuReq;
import com.wqm.result.ResponseResult;
import com.wqm.service.MenuService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("list")
    public ResponseResult getMenuList(String status,String menuName){

        return ResponseResult.okResult( menuService.getMenuList(status,menuName));
    }
    @PostMapping("add")
    public ResponseResult addMenu(@RequestBody MenuReq menuReq){
        menuService.addMenu(menuReq);
        return ResponseResult.okResult();
    }
    @GetMapping("{id}")
    public ResponseResult menuQuery(@PathVariable("id") Long id){
        return ResponseResult.okResult(menuService.menuQuery(id));
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody MenuQuery menuQuery){
        menuService.updateMenu(menuQuery);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId") Long menuId){
        menuService.deleteMenu(menuId);
        return ResponseResult.okResult();
    }
    @GetMapping("/treeselect")
    public ResponseResult getTreeLSelect(){
        return ResponseResult.okResult(menuService.getTreeLSelect());
    }
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult getTreeLSelectById(@PathVariable("id") Long id){
        return ResponseResult.okResult(menuService.getTreeLSelectById(id));
    }

}
