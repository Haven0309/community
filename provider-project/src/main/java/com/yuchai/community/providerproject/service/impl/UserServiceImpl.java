package com.yuchai.community.providerproject.service.impl;

import com.yuchai.community.providerproject.entity.User;
import com.yuchai.community.providerproject.mapper.UserMapper;
import com.yuchai.community.providerproject.service.UserService;
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
