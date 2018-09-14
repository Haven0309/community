package com.yuchai.community.providerproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.providerproject.entity.Project;
import com.yuchai.community.providerproject.entity.ProjectUser;
import com.yuchai.community.providerproject.service.ProjectService;
import com.yuchai.community.providerproject.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Haven
 * @since 2018-09-14
 */
@CrossOrigin
@RestController
@RequestMapping("/projectUser")
public class ProjectUserController {
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private ProjectService projectService;

    /**
     * 获取用户参与过的项目
     * @param id
     * @return
     */
    @GetMapping("/list/{id}")
    public Result list(@PathVariable Integer id){
        List<Project> list = new ArrayList<>();
        List<ProjectUser> projectUsers = projectUserService.list(new QueryWrapper<ProjectUser>().eq("user_code",id));
        for (ProjectUser projectUser:projectUsers) {
            Project project = projectService.getById(projectUser.getProjectId());
            list.add(project);
        }
        return ResultUtil.genSuccessResult(list);
    }

    /**
     * 获取一个用户的结算数据
     * @param id
     * @return
     */
    @GetMapping("/salary/user/{id}")
    public Result salaryAll(@PathVariable Integer id){
        List<ProjectUser> projectUsers = projectUserService.list(new QueryWrapper<ProjectUser>().eq("user_code",id));
        return ResultUtil.genSuccessResult(projectUsers);
    }

    /**
     * 获取一个项目的结算数据
     * @param id
     * @return
     */
    @GetMapping("/salary/project/{id}")
    public Result salaryByprojectId(@PathVariable Integer id){
        List<ProjectUser> projectUsers = projectUserService.list(new QueryWrapper<ProjectUser>().eq("project_id",id));
        return ResultUtil.genSuccessResult(projectUsers);
    }
}

