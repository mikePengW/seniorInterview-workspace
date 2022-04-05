package com.chalon.gulimall.product.service;

import com.chalon.gulimall.product.entity.CategoryEntity;
import com.chalon.gulimall.product.vo.Catalog2Vo;

import java.util.List;
import java.util.Map;

/**
 * @author wei.peng
 */
public interface CategoryService {

    List<CategoryEntity> getLevel1Categorys();

    void updateCascade(CategoryEntity category);

    Map<String, List<Catalog2Vo>> getCatalogJson();

}
