package com.diwayou.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author diwayou
 */
@Slf4j
public class Json {

    private static class Holder {
        private static final Json DEFAULT_MAPPER = new Json(Include.NON_NULL);
    }

    private static class TimeFormatHolder {
        private static final Json TIME_FORMAT_MAPPER = new Json(Include.NON_NULL, "yyyy-MM-dd HH:mm:ss");
    }

    private ObjectMapper mapper;

    public Json(Include inclusion) {
        this(inclusion, null);
    }

    public Json(Include inclusion, String timeFormat) {
        mapper = new ObjectMapper();
        //设置输出时包含属性的风格
        mapper.setSerializationInclusion(inclusion);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //禁止使用int代表Enum的order()來反序列化Enum,非常危險
        mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

        if (timeFormat != null) {
            mapper.setDateFormat(new SimpleDateFormat(timeFormat));
        }
    }

    public static Json nonNull() {
        return Holder.DEFAULT_MAPPER;
    }

    public static Json timeNonNull() {
        return TimeFormatHolder.TIME_FORMAT_MAPPER;
    }

    public <T> T fromJson(String jsonString, Type type) throws IOException {
        return mapper.readValue(jsonString, TypeFactory.defaultInstance().constructType(type));
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public JavaType constructParametricType(Class<?> parametrized, Class<?>... parameterClasses) {
        return mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
    }

    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public byte[] toBytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.error("Json转换出错", e);
            return null;
        }
    }

    public <T> Iterable<T> fromJsonToCollection(String jsonString, Class<? extends Iterable> collectionClass, Class<T> clazz) {
        if (jsonString.startsWith("[")) {
            return fromJson(jsonString, constructParametricType(collectionClass, clazz));
        } else {
            return fromJsonToCollection("[" + jsonString + "]", collectionClass, clazz);
        }
    }

    public <T> List<T> fromJsonToList(String jsonString, Class<T> clazz) {
        return (List<T>) this.fromJsonToCollection(jsonString, List.class, clazz);
    }
}
