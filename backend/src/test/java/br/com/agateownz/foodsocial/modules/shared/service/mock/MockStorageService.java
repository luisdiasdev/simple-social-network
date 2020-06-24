package br.com.agateownz.foodsocial.modules.shared.service.mock;

import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import br.com.agateownz.foodsocial.modules.shared.service.StorageService;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class MockStorageService implements StorageService {

    @Override
    public Optional<String> store(String uuid, String folder, String contentType, byte[] fileBuffer, boolean isPublic) {
        return Optional.empty();
    }

    @Override
    public void delete(String filePath) {

    }

    @Override
    public StoreObject get(String filePath) {
        return null;
    }
}
