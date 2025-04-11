package com.wqm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wqm.entity.Link;
import com.wqm.entity.query.LinkQuery;
import com.wqm.entity.vo.LinkVol;
import com.wqm.entity.vo.PageVo;
import com.wqm.result.ResponseResult;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-10-28 14:56:41
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    PageVo getList(String name, String status, Integer pageNum, Integer pageSize);

    void addLink(LinkQuery query);

    LinkVol getLinkById(Long id);

    void updateLink(LinkVol vo);

    void deleteLink(Long id);
}
