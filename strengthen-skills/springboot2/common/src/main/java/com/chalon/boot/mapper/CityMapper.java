package com.chalon.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chalon.boot.bean.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

/**
 * 配置两种方法
 * 1.@Mapper 注册到每个类中
 * 2、启动类添加扫描 @MapperScan("com.chalon.mapper") 指定包下的路径，就不需要每个类单独配置@Mapper就可以了
 *
 * @author wei.peng
 */
@Mapper
public interface CityMapper extends BaseMapper<City> {

    @Select("select * from city where id=#{id}")
    public City getById(Long id);

//    @Insert("insert into city(`name`,`state`,`country`) values(#{name},#{state},#{country})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    public void insert(City city);

}
