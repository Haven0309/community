package com.yuchai.community.providerproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.providerproject.entity.*;
import com.yuchai.community.providerproject.service.*;
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
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectUserService projectUserService;
    @Autowired
    private ProjectTeamService projectTeamService;
    @Autowired
    private TeamsService teamsService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeamUserService teamUserService;

    /**
     * 创建项目
     *
     * @param project
     * @return
     */
    @PostMapping("/add")
    public Result add(Project project) {
        boolean save = projectService.save(project);
        if (save) {
            return ResultUtil.genSuccessResult();
        }
        return ResultUtil.genFailResult("插入失败");

    }

    /**
     * 获取单个项目信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Integer id) {
        return ResultUtil.genSuccessResult(projectService.getById(id));
    }

    /**
     * 分页查询全部项目
     *
     * @param pageInt  页码
     * @param pageSize 数量
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam int pageInt,
                       @RequestParam int pageSize) {
        Page<Project> page = new Page<>(pageInt, pageSize);
        IPage<Project> projectIPage = projectService.page(page, null);
        List<Project> list = projectIPage.getRecords();
        for (Project project : list) {
            List<ProjectTeam> projectTeams = projectTeamService.list(new QueryWrapper<ProjectTeam>().eq("project_id", project.getId()));
            List<Teams> teams = new ArrayList<>();
            List<User> users = new ArrayList<>();
            for (ProjectTeam pt : projectTeams) {
                Integer teamId = pt.getTeamId();
                teams.add(teamsService.getById(teamId));
                List<TeamUser> teamUsers = teamUserService.list(new QueryWrapper<TeamUser>().eq("team_id", teamId));
                for (TeamUser tu : teamUsers) {
                    User user = userService.getOne(new QueryWrapper<User>().eq("user_code", tu.getUserCode()));
                    users.add(user);
                }
            }
            project.setTeams(teams);
            project.setUsers(users);

        }
        return ResultUtil.genSuccessResult(list);
    }

    public Result search(@RequestParam int pageInt,
                         @RequestParam int pageSize) {
        Page<Project> page = new Page<>(pageInt, pageSize);
        IPage<Project> projectIPage = projectService.page(page, new QueryWrapper<Project>());
        List<Project> list = projectIPage.getRecords();
        for (Project project : list) {
            List<ProjectTeam> projectTeams = projectTeamService.list(new QueryWrapper<ProjectTeam>().eq("project_id", project.getId()));
            List<Teams> teams = new ArrayList<>();
            List<User> users = new ArrayList<>();
            for (ProjectTeam pt : projectTeams) {
                Integer teamId = pt.getTeamId();
                teams.add(teamsService.getById(teamId));
                List<TeamUser> teamUsers = teamUserService.list(new QueryWrapper<TeamUser>().eq("team_id", teamId));
                for (TeamUser tu : teamUsers) {
                    User user = userService.getOne(new QueryWrapper<User>().eq("user_code", tu.getUserCode()));
                    users.add(user);
                }
            }
            project.setTeams(teams);
            project.setUsers(users);

        }
        return ResultUtil.genSuccessResult(list);
    }
}

