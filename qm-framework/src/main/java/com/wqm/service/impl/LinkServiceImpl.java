package com.wqm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wqm.entity.Category;
import com.wqm.entity.Link;
import com.wqm.entity.SystemConstants;
import com.wqm.entity.query.LinkQuery;
import com.wqm.entity.vo.LinkVol;
import com.wqm.entity.vo.PageVo;
import com.wqm.mapper.LinkMapper;
import com.wqm.result.ResponseResult;
import com.wqm.service.LinkService;
import com.wqm.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-10-28 14:56:41
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        List<LinkVol> linkVo =  BeanCopyUtils.copyBeanList(links,LinkVol.class);

        return ResponseResult.okResult(linkVo);
    }

    @Override
    public PageVo getList(String name, String status, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper queryWrapper =     new LambdaQueryWrapper<Link>().eq(Link::getDelFlag,0)
                .like(StringUtils.hasText(name),Link::getName,name)
                .like(StringUtils.hasText(status),Link::getStatus,status);
        Page page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        return new PageVo(page.getRecords(),page.getTotal());
    }

    @Override
    public void addLink(LinkQuery query) {
        Link link = BeanCopyUtils.copyBean(query, Link.class);
        baseMapper.insert(link);
    }

    @Override
    public LinkVol getLinkById(Long id) {
        Link link = baseMapper.selectById(id);
        LinkVol linkVol = BeanCopyUtils.copyBean(link, LinkVol.class);
        return linkVol;
    }

    @Override
    public void updateLink(LinkVol vo) {
       baseMapper.updateById(BeanCopyUtils.copyBean(vo, Link.class)) ;
    }

    @Override
    public void deleteLink(Long id) {
        baseMapper.deleteById(id);
    }
}
