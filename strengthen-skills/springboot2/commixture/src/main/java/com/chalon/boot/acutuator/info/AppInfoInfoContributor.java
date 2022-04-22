package com.chalon.boot.acutuator.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author wei.peng
 */
@Component
public class AppInfoInfoContributor implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("msg", "你好")
                .withDetail("hello", "chalon")
                .withDetails(Collections.singletonMap("world", "666888999"));
    }

}
