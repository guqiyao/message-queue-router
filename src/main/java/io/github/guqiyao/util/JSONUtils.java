package io.github.guqiyao.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author qiyao.gu@qq.com.
 */
public final class JSONUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JSONUtils() {
    }

    public static <T> T toBean(String json, Class<T> clazz) throws IOException {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}