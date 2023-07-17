package ir.tinyLink.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;


@Log4j2
public class JsonUtil {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
                .configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(
                        new SimpleModule().addSerializer(Double.class, new JsonSerializer<Double>() {
                            @Override
                            public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                                jsonGenerator.writeNumber(new BigDecimal(value.toString()).toPlainString());
                            }
                        })
                );
    }

    public static String getWithNoTimestampJson(Object obj) {
        try {
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.warn("unsuccessful parsing json", e);
            throw new TinyLinkGeneralException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.errorInternalServer());
        }
    }


}

