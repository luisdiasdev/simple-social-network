package br.com.agateownz.foodsocial.modules.shared;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

public class MultipartFileMockBuilders {

    public static MockMultipartFile mockMultipartFile(String name) throws Exception {
        return new MockMultipartFile(name, new ClassPathResource("data/image.png").getInputStream());
    }
}
