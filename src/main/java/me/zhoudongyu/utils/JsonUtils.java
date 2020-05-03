package me.zhoudongyu.utils;

import me.zhoudongyu.core.consts.StringConsts;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * 处理Json的工具类
 */
public class JsonUtils {

    private static final Logger logger = LogManager.getLogger(JsonUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .setDateFormat(new SimpleDateFormat(StringConsts.TIMESTAMP_PATTERN))
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private static JsonNodeFactory jnf = new JsonNodeFactory(false);

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String toPJson(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unused")
    public static Map<String, Object> toMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static Map<String, String> toMapSS(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<Object> toList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Object>>() {
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static JsonNode read(InputStream is) {
        try {
            return objectMapper.readTree(is);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static JsonNode read(String str) {
        try {
            return objectMapper.readTree(new ByteArrayInputStream(str.getBytes(Charset.forName(StringConsts.UTF8))));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unused")
    public static ArrayNode readArray(String str) {
        return (ArrayNode) read(str);
    }

    @SuppressWarnings("unused")
    public static ArrayNode readArray(InputStream is) {
        return (ArrayNode) read(is);
    }

    @SuppressWarnings("unused")
    public static ObjectNode readObject(String str) {
        return (ObjectNode) read(str);
    }

    @SuppressWarnings("unused")
    public static ObjectNode readObject(InputStream is) {
        return (ObjectNode) read(is);
    }

    public static ObjectNode createObjectNode() {
        return jnf.objectNode();
    }

    public static ArrayNode createArrayNode() {
        return jnf.arrayNode();
    }

}
