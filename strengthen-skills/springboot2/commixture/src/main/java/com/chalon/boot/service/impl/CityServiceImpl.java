package com.chalon.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chalon.boot.bean.City;
import com.chalon.boot.mapper.CityMapper;
import com.chalon.boot.service.CityService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wei.peng
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {

    @Autowired
    CityMapper cityMapper;

    Counter counter;

    public CityServiceImpl(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("cityService.saveCity.count");
    }

    public void saveCity(City city) {
        counter.increment();
        cityMapper.insert(city);
    }

}
