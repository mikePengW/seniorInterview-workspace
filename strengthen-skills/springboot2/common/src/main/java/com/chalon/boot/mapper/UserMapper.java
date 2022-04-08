package com.chalon.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chalon.boot.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author wei.peng
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
