package com.yuchai.community.providerteam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.providerteam.entity.Teams;
import com.yuchai.community.providerteam.service.TeamsService;
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
@RequestMapping("/teams")
public class TeamsController {
    @Autowired
    private TeamsService teamsService;

    @PostMapping("/add")
    public boolean add(@RequestBody Teams team) {
        return teamsService.save(team);
    }

    @GetMapping("/list")
    public List<Teams> list(@RequestParam(required = false,defaultValue = "") String name) {
        return teamsService.list(new QueryWrapper<Teams>().like("team_name",name));
    }
}

