package br.com.agateownz.foodsocial.modules.shared.dto;

import java.io.InputStream;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

public class MockStoreObject implements StoreObject {

    private final ClassPathResource resource;

    public MockStoreObject(ClassPathResource resource) {
        this.resource = resource;
    }

    @Override
    @SneakyThrows
    public InputStream getInputStream() {
        return resource.getInputStream();
    }
}