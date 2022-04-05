package com.chalon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chalon.bean.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wei.peng
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    public Account getAcct(Long id);

}
