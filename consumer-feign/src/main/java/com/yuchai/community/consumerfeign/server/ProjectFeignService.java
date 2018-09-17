package com.yuchai.community.consumerfeign.server;

import com.yuchai.community.communitycore.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Haven
 * @create 2018-09-16 16:52
 */
@FeignClient("community-provider-project")
public interface ProjectFeignService {
    /**
     * 分页查询全部项目
     *
     * @param pageInt  页码
     * @param pageSize 数量
     * @return
     */
    @RequestMapping(value = "/project/list",method = RequestMethod.GET)
    List<ProjectVO> list(@RequestParam("pageInt") int pageInt, @RequestParam("pageSize") int pageSize);

    /**
     * 获取单个项目信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/project/get/{id}",method = RequestMethod.GET)
    ProjectVO get(@PathVariable("id") Integer id);

}
