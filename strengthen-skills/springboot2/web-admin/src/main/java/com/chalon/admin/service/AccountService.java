package com.chalon.admin.service;

import com.chalon.admin.bean.Account;
import com.chalon.admin.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface AccountService {

    Account getAcctByid(Long id);
}
