package br.com.agateownz.foodsocial.modules.shared.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = UuidService.class)
class UuidServiceTest {

    @Autowired
    private UuidService uuidService;

    @DisplayName("getRandomUuuid should return different values each time")
    @Test
    public void getRandomUuidTest() {
        var uuid1 = uuidService.getRandomUuuid();
        var uuid2 = uuidService.getRandomUuuid();

        assertThat(uuid1).isNotEqualTo(uuid2);
    }
}