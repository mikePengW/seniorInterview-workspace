package com.chalon.boot.mybatisplus;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

/**
 * @author wei.peng wrote on 2024-05-20
 * @version 1.0
 */
public class Test1 {

    public void test1(String target) {
        MetaObject handler = SystemMetaObject.forObject(target);
        while (handler.hasGetter("h")) {
            Object object = handler.getValue("h");
            handler = SystemMetaObject.forObject(object);
        }
        while (handler.hasGetter("target")) {
            Object object = handler.getValue("target");
            handler = SystemMetaObject.forObject(object);
        }
    }

}
