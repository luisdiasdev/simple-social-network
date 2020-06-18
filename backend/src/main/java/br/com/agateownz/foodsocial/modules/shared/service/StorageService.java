package br.com.agateownz.foodsocial.modules.shared.service;

import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import java.util.Optional;

public interface StorageService {

    Optional<String> store(
        String uuid,
        String folder,
        String contentType,
        byte[] fileBuffer,
        boolean isPublic);

    void delete(String filePath);

    StoreObject get(String filePath);
}
