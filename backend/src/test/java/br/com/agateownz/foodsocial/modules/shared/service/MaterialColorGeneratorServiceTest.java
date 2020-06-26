package br.com.agateownz.foodsocial.modules.shared.service;

import br.com.agateownz.foodsocial.config.ApplicationProfiles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles(ApplicationProfiles.TEST)
@SpringBootTest(classes = {MaterialColorGeneratorService.class, ObjectMapper.class})
class MaterialColorGeneratorServiceTest {

    @Autowired
    private MaterialColorGeneratorService materialColorGeneratorService;

    @DisplayName("getRandomMaterialColor should return valid color")
    @Test
    public void materialColorTest() {
        var result = materialColorGeneratorService.getRandomMaterialColor();

        assertThat(result).isNotNull();
    }
}