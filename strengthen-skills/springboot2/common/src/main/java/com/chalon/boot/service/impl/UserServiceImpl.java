package com.chalon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chalon.bean.User;
import com.chalon.mapper.UserMapper;
import com.chalon.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author wei.peng
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
