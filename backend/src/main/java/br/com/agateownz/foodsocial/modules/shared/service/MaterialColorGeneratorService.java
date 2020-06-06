package br.com.agateownz.foodsocial.modules.shared.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaterialColorGeneratorService {

    @Autowired
    private ResourceLoader resourceResolver;

    @Autowired
    private ObjectMapper objectMapper;

    private List<String> materialColors;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void initializeMaterialColors() throws IOException {
        var resource = getMaterialColorsJson().orElseThrow();
        var is = resource.getURL();
        var keyType = objectMapper.getTypeFactory().constructType(String.class);
        var colorType = objectMapper.getTypeFactory().constructMapType(HashMap.class, keyType, keyType);
        var type = objectMapper.getTypeFactory().constructMapType(HashMap.class, keyType, colorType);
        var result = (Map<String, Map<String, String>>) objectMapper.readValue(is, type);
        materialColors = result.values()
                .stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .collect(Collectors.toList());
    }

    public String getRandomMaterialColor() {
        var random = new Random(new Date().getTime());
        var next = random.nextInt(materialColors.size());
        return materialColors.get(next);
    }

    private Optional<Resource> getMaterialColorsJson() {
        return Optional.of(resourceResolver.getResource("classpath:material-colors.json"));
    }
}
