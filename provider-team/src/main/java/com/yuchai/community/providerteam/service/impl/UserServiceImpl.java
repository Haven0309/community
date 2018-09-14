package com.yuchai.community.providerteam.service.impl;

import com.yuchai.community.providerteam.entity.User;
import com.yuchai.community.providerteam.mapper.UserMapper;
import com.yuchai.community.providerteam.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Haven
 * @since 2018-09-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
