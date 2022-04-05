package com.chalon.admin.mapper;

import com.chalon.admin.bean.Account;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface AccountMapper {


    public Account getAcct(Long id);
}
