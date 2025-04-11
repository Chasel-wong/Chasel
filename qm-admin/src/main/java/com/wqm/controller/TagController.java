package com.wqm.controller;

import com.wqm.domain.TagListDto;
import com.wqm.entity.vo.PageVo;
import com.wqm.entity.vo.TagVo;
import com.wqm.result.ResponseResult;
import com.wqm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        tagService.deleteTag(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult getTagInfo(@PathVariable("id") Long id){
        return tagService.getTagInfo(id);
    }
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagVo tagVo){
      tagService.updateTag(tagVo);
      return ResponseResult.okResult();
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
//
}
