package com.chalon.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chalon.boot.bean.User;
import com.chalon.boot.mapper.UserMapper;
import com.chalon.boot.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author wei.peng
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
