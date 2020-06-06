package br.com.agateownz.foodsocial.config.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StringToMapConverterImpl implements StringToMapConverter {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map convert(String content) {
        try {
            return objectMapper.readValue(content, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert map to string", e);
        }
    }
}
