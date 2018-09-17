package com.yuchai.community.providerproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.communitycore.vo.ProjectVO;
import com.yuchai.community.communitycore.vo.TeamsVO;
import com.yuchai.community.communitycore.vo.UserVO;
import com.yuchai.community.providerproject.entity.*;
import com.yuchai.community.providerproject.service.*;
import org.springframework.beans.BeanUtils;
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
    public boolean add(@RequestBody Project project) {
        return projectService.save(project);

    }

    /**
     * 获取单个项目信息
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ProjectVO get(@PathVariable Integer id) {
        ProjectVO projectVO = new ProjectVO();
        BeanUtils.copyProperties(projectService.getById(id),projectVO);
        List<ProjectTeam> projectTeams = projectTeamService.list(new QueryWrapper<ProjectTeam>().eq("project_id", id));
        List<TeamsVO> teamsVOS = new ArrayList<>();
        foreachProjectTeams(projectTeams, teamsVOS);
        projectVO.setTeams(teamsVOS);
        return projectVO;
    }

    /**
     * 分页查询全部项目
     *
     * @param pageInt  页码
     * @param pageSize 数量
     * @return
     */
    @GetMapping("/list")
    public List<ProjectVO> list(@RequestParam int pageInt,
                              @RequestParam int pageSize) {
        Page<Project> page = new Page<>(pageInt, pageSize);
        IPage<Project> projectIPage = projectService.page(page, null);
        List<Project> projects = projectIPage.getRecords();
        List<ProjectVO> projectVOList = new ArrayList<>();
        for (Project project : projects) {
            ProjectVO projectVO = new ProjectVO();
            BeanUtils.copyProperties(project,projectVO);
            List<ProjectTeam> projectTeams = projectTeamService.list(new QueryWrapper<ProjectTeam>().eq("project_id", project.getId()));
            List<TeamsVO> teamsVOS = new ArrayList<>();
            foreachProjectTeams(projectTeams, teamsVOS);
            projectVO.setTeams(teamsVOS);
            projectVOList.add(projectVO);
        }

        return projectVOList;
    }

    private void foreachProjectTeams(List<ProjectTeam> projectTeams, List<TeamsVO> teamsVOS) {
        for (ProjectTeam projectTeam : projectTeams) {
            Teams team = teamsService.getById(projectTeam.getTeamId());
            TeamsVO teamsVO = new TeamsVO();
            BeanUtils.copyProperties(team,teamsVO);
            List<UserVO> userVOS = new ArrayList<>();
            List<TeamUser> teamUsers = teamUserService.list(new QueryWrapper<TeamUser>().eq("team_id", team.getId()));
            for (TeamUser teamUser : teamUsers) {
                User user = userService.getOne(new QueryWrapper<User>().eq("user_code", teamUser.getUserCode()));
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(user,userVO);
                userVOS.add(userVO);
            }
            teamsVO.setUsers(userVOS);
            teamsVOS.add(teamsVO);
        }
    }

}

