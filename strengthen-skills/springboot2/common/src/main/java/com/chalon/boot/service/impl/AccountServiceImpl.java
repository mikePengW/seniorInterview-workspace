package com.chalon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chalon.bean.Account;
import com.chalon.mapper.AccountMapper;
import com.chalon.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @author wei.peng
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
