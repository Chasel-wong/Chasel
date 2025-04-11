package com.wqm.controller;

import com.wqm.entity.query.LinkQuery;
import com.wqm.entity.vo.LinkVol;
import com.wqm.result.ResponseResult;
import com.wqm.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult getList(@RequestParam(required = false)String name, @RequestParam(required = false)String status, Integer pageNum, Integer pageSize){
            return ResponseResult.okResult(linkService.getList(name,status,pageNum,pageSize));
    }
    @PostMapping
    public  ResponseResult addLink(@RequestBody LinkQuery query){
        linkService.addLink(query);
        return ResponseResult.okResult();
    }
    @GetMapping("{id}")
    public ResponseResult getLinkById(@PathVariable("id") Long id){
        return ResponseResult.okResult(linkService.getLinkById(id));
    }
    @PutMapping
    public ResponseResult updateLink(@RequestBody LinkVol vo){
        linkService.updateLink(vo);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id){
        linkService.deleteLink(id);
        return ResponseResult.okResult();
    }
}
