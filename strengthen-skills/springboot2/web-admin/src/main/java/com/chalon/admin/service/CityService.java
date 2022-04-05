package com.chalon.admin.service;

import com.chalon.admin.bean.City;
import com.chalon.admin.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface CityService {

    City getById(Long id);

    void saveCity(City city);

}
