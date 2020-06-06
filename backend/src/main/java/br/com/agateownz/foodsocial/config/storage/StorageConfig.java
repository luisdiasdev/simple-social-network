package br.com.agateownz.foodsocial.config.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties("app.storage")
public class StorageConfig {

    @NotBlank
    private String path;
}
