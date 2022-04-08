package com.chalon.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chalon.boot.bean.Account;
import com.chalon.boot.mapper.AccountMapper;
import com.chalon.boot.service.AccountService;
import org.springframework.stereotype.Service;

/**
 * @author wei.peng
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

}
