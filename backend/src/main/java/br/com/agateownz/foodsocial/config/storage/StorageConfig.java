package br.com.agateownz.foodsocial.config.storage;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("app.storage")
public class StorageConfig {

    @NotBlank
    private String path;
}
