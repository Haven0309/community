package com.yuchai.community.providerproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuchai.community.providerproject.entity.ProjectTeam;
import com.yuchai.community.providerproject.service.ProjectTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Haven
 * @since 2018-09-14
 */
@RestController
@RequestMapping("/projectTeam")
public class ProjectTeamController {
    @Autowired
    private ProjectTeamService projectTeamService;

    @GetMapping("/project/{id}")
    public List<ProjectTeam> getTeamByProjectId(@PathVariable Integer id){
        return projectTeamService.list(new QueryWrapper<ProjectTeam>().eq("project_id",id));
    }
}

