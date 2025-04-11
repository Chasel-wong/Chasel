package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.domain.TagListDto;
import com.wqm.entity.Tag;
import com.wqm.entity.vo.PageVo;
import com.wqm.entity.vo.TagVo;
import com.wqm.result.ResponseResult;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2025-01-14 12:53:52
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);

    ResponseResult getTagInfo(Long id);


    ResponseResult updateTag(TagVo tagVo);

    ResponseResult listAllTag();
}
