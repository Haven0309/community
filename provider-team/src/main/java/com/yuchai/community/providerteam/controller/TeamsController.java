package com.yuchai.community.providerteam.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuchai.community.communitycore.util.Result;
import com.yuchai.community.communitycore.util.ResultUtil;
import com.yuchai.community.providerteam.entity.Teams;
import com.yuchai.community.providerteam.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result add(Teams team) {
        boolean save = teamsService.save(team);
        if (save) {
            return ResultUtil.genSuccessResult();
        }
        return ResultUtil.genFailResult("插入失败");
    }

    @GetMapping("/list")
    public Result list() {
        return ResultUtil.genSuccessResult(teamsService.list(new QueryWrapper<Teams>()));
    }
}

