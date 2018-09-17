package com.yuchai.community.providerteam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuchai.community.providerteam.entity.TeamUser;
import com.yuchai.community.providerteam.service.TeamUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/teamUser")
public class TeamUserController {
    @Autowired
    private TeamUserService teamUserService;

    @PostMapping("/add")
    public boolean add(@RequestBody TeamUser teamUser) {
        return teamUserService.save(teamUser);
    }
    @GetMapping("/list/{id}")
    public List<TeamUser> list(@PathVariable Integer id){
        return teamUserService.list(new QueryWrapper<TeamUser>().eq("team_id",id));
    }

}

