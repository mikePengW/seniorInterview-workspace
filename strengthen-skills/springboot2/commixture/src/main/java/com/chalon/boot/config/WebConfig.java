package com.chalon.boot.config;

import com.chalon.boot.bean.Pet;
import com.chalon.boot.converter.ChalonMessageConverter;
import com.chalon.boot.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei.peng
 * @EnableWebMvc：全面接管 1、静态资源？视图解析器？欢迎页......全部失效
 */
//@EnableWebMvc
@Configuration(proxyBeanMethods = false)
public class WebConfig {
    /**
     * Filter、Interceptor 几乎拥有相同的功能？区别呢？
     * 1、Filter是Servlet定义的原生组件。好处，脱离Spring应用也能使用
     * 2、Interceptor是Spring定义的接口。可以使用Spring的自动装配等功能
     */
//    @Autowired
//    RedisUrlCountInterceptor redisUrlCountInterceptor;

    // 1、WebMvcConfigurer 定制化SpringMVC的功能
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * 定义静态资源行为
             * @param registry
             */
//            @Override
//            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                /**
//                 * 访问 /aa/** 所有请求都去 classpath:/static/ 下面进行匹配
//                 */
//                registry.addResourceHandler("/aa/**")
//                        .addResourceLocations("classpath:/static/");
//            }

            /**
             * 1、编写一个拦截器实现HandlerInterceptor接口
             * 2、拦截器注册到容器中（实现WebMvcConfigurer的addInterceptors）
             * 3、指定拦截规则【如果拦截所有，静态资源也会被拦截】
             * @param registry
             */
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor())
                        .addPathPatterns("/**") // 所有请求都被拦截包括静态资源
                        .excludePathPatterns("/", "/login", "/css/**", "/fonts/**", "/images/**",
                                "/js/**", "/aa/**"); // 放行的请求
                //
//                registry.addInterceptor(redisUrlCountInterceptor)
//                        .addPathPatterns("/**")
//                        .excludePathPatterns("/", "/login", "/css/**", "/fonts/**", "/images/**",
//                                "/js/**", "/aa/**");
            }

            /**
             * 自定义内容协商策略
             * @param configurer
             */
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                // Map<String, MediaType> mediaTypes
                Map<String, MediaType> mediaTypes = new HashMap<>();
                mediaTypes.put("json", MediaType.APPLICATION_JSON);
                mediaTypes.put("xml", MediaType.APPLICATION_XML);
                mediaTypes.put("cl", MediaType.parseMediaType("application/x-chalon"));
                // 指定支持解析哪些参数对应的哪些媒体类型
                ParameterContentNegotiationStrategy parameterStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
//                parameterStrategy.setParameterName("ff");
                // 头解析策略
                HeaderContentNegotiationStrategy headerStrategy = new HeaderContentNegotiationStrategy();
                //
                configurer.strategies(Arrays.asList(parameterStrategy, headerStrategy));
            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new ChalonMessageConverter());
            }

            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                // 不移除; 后面的内容。矩阵变量功能就可以生效
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Pet>() {

                    @Override
                    public Pet convert(String source) {
                        // 阿猫,3
                        if (!StringUtils.isEmpty(source)) {
                            Pet pet = new Pet();
                            String[] split = source.split(",");
                            pet.setName(split[0]);
                            pet.setAge(Integer.parseInt(split[1]));
                            return pet;
                        }
                        return null;
                    }
                });
            }
        };
    }

//    @Bean
//    public WebMvcRegistrations webMvcRegistrations() {
//        return new WebMvcRegistrations() {
//            @Override
//            public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
//                return null;
//            }
//        };
//    }

}
