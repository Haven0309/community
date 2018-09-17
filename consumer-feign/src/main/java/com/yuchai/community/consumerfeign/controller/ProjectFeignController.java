package com.yuchai.community.consumerfeign.controller;

import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.communitycore.vo.ProjectVO;
import com.yuchai.community.consumerfeign.server.ProjectFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Haven
 * @create 2018-09-16 16:54
 */
@RestController
@RequestMapping("/project")
public class ProjectFeignController {
    @Autowired
    private ProjectFeignService projectFeignService;

    @GetMapping("/list")
    public Result list(@RequestParam int pageInt,
                       @RequestParam int pageSize){
        List<ProjectVO> projects = projectFeignService.list(pageInt, pageSize);
        return ResultUtil.genSuccessResult(projects);
    }

    /**
     * 获取单个项目信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Integer id){
        return ResultUtil.genSuccessResult(projectFeignService.get(id));
    }
}
