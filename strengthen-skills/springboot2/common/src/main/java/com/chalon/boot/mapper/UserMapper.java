package com.chalon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chalon.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wei.peng
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
