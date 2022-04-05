package com.chalon.admin.service.impl;

import com.chalon.admin.bean.User;
import com.chalon.admin.mapper.UserMapper;
import com.chalon.admin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


}
