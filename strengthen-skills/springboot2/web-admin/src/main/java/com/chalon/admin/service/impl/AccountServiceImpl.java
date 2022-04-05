package com.chalon.admin.service.impl;

import com.chalon.admin.bean.Account;
import com.chalon.admin.mapper.AccountMapper;
import com.chalon.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    public Account getAcctByid(Long id) {
        return accountMapper.getAcct(id);
    }
}
