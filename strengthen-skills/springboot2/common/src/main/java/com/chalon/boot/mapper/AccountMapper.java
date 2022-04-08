package com.chalon.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chalon.boot.bean.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wei.peng
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    public Account getAcct(Long id);

}
