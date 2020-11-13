package com.springcloud.study.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class Serial2JsonConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
            // 保留 Map 空的字段
            SerializerFeature.WriteMapNullValue,
            // 将 String 类型的 null 转成""
            SerializerFeature.WriteNullStringAsEmpty,
            // 将 Number 类型的 null 转成 0
            SerializerFeature.WriteNullNumberAsZero,
            // 将 List 类型的 null 转成 []
            SerializerFeature.WriteNullListAsEmpty,
            // 将 Boolean 类型的 null 转成 false
            SerializerFeature.WriteNullBooleanAsFalse,
            // 避免循环引用
            SerializerFeature.DisableCircularReferenceDetect);
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, com.alibaba.fastjson.serializer.ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        config.setSerializeFilters((ValueFilter)(object, name, value) -> {
            //如果类型为Date且为null，返回空串
            if (value == null && isDate(getType(object, name))) {
                return "";
            }
            return value;
        });
        config.setSerializeConfig(serializeConfig);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(StandardCharsets.UTF_8);

        List<MediaType> mediaTypeList = new ArrayList<>();
        // 解决中文乱码问题，相当于在 Controller 上的 @RequestMapping 中加了个属性 produces = "application/json;charset=UTF-8"
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(mediaTypeList);
        converters.add(converter);
    }

    String getType(Object object, String value) {
        Field[] fields = object.getClass().getDeclaredFields();
        String type = null;
        String name;
        for (Field field : fields) {
            name = field.getName();
            if (StringUtils.equals(name, value)) {
                type = field.getType().getTypeName();
                break;
            }
        }
        return type;
    }

    private boolean isDate(String type) {
        final String var = "java.util.Date";
        return var.equals(type);
    }
}
