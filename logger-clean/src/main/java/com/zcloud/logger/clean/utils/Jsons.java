package com.zcloud.logger.clean.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Jsons {

    private static Logger logger = LoggerFactory.getLogger(Jsons.class);

    private static final ObjectMapper objectMapper;
    private static final JacksonJsonProvider jjp;

    static {
        objectMapper = new ObjectMapper();
        jjp = new JacksonJsonProvider(objectMapper);
    }

    public static String objectToJSONStr(Object obj) {
        if (obj == null)
            return null;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Failed objectToJSONStr object is : " + obj, e);
            return null;
        }
    }

    public static String objectToPrettyJSONStr(Object obj) {
        if (obj == null)
            return null;
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("Failed objectToPrettyJSONStr object is : " + obj, e);
            return null;
        }
    }

    public static <T> T objectFromJSONStr(String str, JavaType javaType) {
        if (str == null || str.length() == 0)
            return null;
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            //logger.error("Failed objectFromJSONStr jsonStr is : " + str + ", javaType is : " + javaType.getRawClass().getCanonicalName(), e);
            return null;
        }
    }

    public static <T> T objectFromJSONStr(String str, Class<T> type) {
        if (str == null || str.length() == 0)
            return null;
        try {
            return objectMapper.readValue(str, type);
        } catch (Exception e) {
            //logger.error("Failed objectFromJSONStr jsonStr is : " + str + ", type is : " + type.getCanonicalName(), e);
            return null;
        }
    }

    public static <T> List<T> listFromJSONStr(String str, Class<T> type) {
        List<T> list = new ArrayList<T>();
        @SuppressWarnings("unchecked")
        List<Object> ncs = Jsons.objectFromJSONStr(str, List.class);
        if (ncs != null && ncs.size() > 0) {
            for (Object nc : ncs) {
                T item = Jsons.objectFromJSONStr(
                        Jsons.objectToJSONStr(nc), type);
                if (item != null)
                    list.add(item);
            }
        }
        return list;
    }

}
