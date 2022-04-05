package com.chalon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chalon.bean.City;
import com.chalon.mapper.CityMapper;
import com.chalon.service.CityService;
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
