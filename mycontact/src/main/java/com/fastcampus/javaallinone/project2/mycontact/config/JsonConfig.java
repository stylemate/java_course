package com.fastcampus.javaallinone.project2.mycontact.config;

import com.fastcampus.javaallinone.project2.mycontact.config.serializer.BirthdaySerializer;
import com.fastcampus.javaallinone.project2.mycontact.domain.dto.Birthday;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JsonConfig {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BirthdayModule());

        return objectMapper;
    }

    static class BirthdayModule extends SimpleModule {
        BirthdayModule() {
            super();
            addSerializer(Birthday.class, new BirthdaySerializer());
        }
    }
}
