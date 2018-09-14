package com.yuchai.community.providerteam.controller;


import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.providerteam.entity.TeamUser;
import com.yuchai.community.providerteam.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/teamUser")
public class TeamUserController {
    @Autowired
    private TeamUserService teamUserService;

    @PostMapping("/add")
    public Result add(TeamUser teamUser) {
        return ResultUtil.genSuccessResult(teamUserService.save(teamUser));
    }

}

