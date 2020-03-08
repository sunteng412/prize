package com.mrfox.prize.configuration.cache;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

@Slf4j
public class JacksonHelper {

    private static final SimpleModule module = initModule();
    private static final ObjectMapper objectMapper;
    private static final ObjectMapper prettyMapper;

    public JacksonHelper() {
    }

    private static SimpleModule initModule() {
        return (new SimpleModule()).addSerializer(BigDecimal.class, new BigDecimalSerializer())
                .addSerializer(LocalTime.class, new LocalTimeSerializer())
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer())
                .addSerializer(LocalDate.class, new LocalDateSerializer())
                .addDeserializer(LocalDate.class, new LocalDateDeserializer())
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer())
                .addSerializer(Date.class, new DateSerializer())
                .addDeserializer(Date.class, new DateDeserializer());
    }

    public static JavaType genJavaType(TypeReference<?> typeReference) {
        return getObjectMapper().getTypeFactory().constructType(typeReference.getType());
    }

    public static JavaType genJavaType(Class<?> clazz) {
        return getObjectMapper().getTypeFactory().constructType(clazz);
    }

    public static JavaType genCollectionType(Class<? extends Collection> collectionClazz, Class<?> javaClazz) {
        return getObjectMapper().getTypeFactory().constructCollectionType(collectionClazz, javaClazz);
    }

    public static JavaType genMapType(Class<? extends Map> mapClazz, Class<?> keyClass, Class<?> valueClazz) {
        return getObjectMapper().getTypeFactory().constructMapType(mapClazz, keyClass, valueClazz);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static ObjectMapper getPrettyMapper() {
        return prettyMapper;
    }

    static {
        objectMapper = (new ObjectMapper()).registerModule(module).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        prettyMapper = objectMapper.copy().configure(SerializationFeature.INDENT_OUTPUT, true);
    }

}


class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    public LocalDateDeserializer() {
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateString = ((JsonNode) jp.getCodec().readTree(jp)).asText();
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

}


class DateDeserializer extends JsonDeserializer<Date> {

    public DateDeserializer() {
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateTimeStr = ((JsonNode) jp.getCodec().readTree(jp)).asText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        return sdf.parse(dateTimeStr, pos);
    }

}


class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public LocalDateTimeDeserializer() {
    }

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateTimeStr = ((JsonNode) jp.getCodec().readTree(jp)).asText();
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}


class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    public LocalTimeDeserializer() {
    }
    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String dateString = ((JsonNode) jp.getCodec().readTree(jp)).asText();
        return LocalTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_TIME);
    }

}


class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    public BigDecimalSerializer() {
    }
    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.toString());
    }

}


class LocalDateSerializer extends JsonSerializer<LocalDate> {

    public LocalDateSerializer() {
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(DateTimeFormatter.ISO_LOCAL_DATE.format(value));
    }

}


class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    public LocalDateTimeSerializer() {
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
    }

}


class DateSerializer extends JsonSerializer<Date> {

    public DateSerializer() {
    }

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jgen.writeString(sdf.format(value));
    }

}


class LocalTimeSerializer extends JsonSerializer<LocalTime> {

    public LocalTimeSerializer() {
    }

    @Override
    public void serialize(LocalTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(DateTimeFormatter.ISO_LOCAL_TIME.format(value));
    }

}