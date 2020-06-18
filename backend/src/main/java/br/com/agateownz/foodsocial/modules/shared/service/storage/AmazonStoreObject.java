package br.com.agateownz.foodsocial.modules.shared.service.storage;

import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import com.amazonaws.services.s3.model.S3Object;
import java.io.InputStream;
import lombok.Data;

@Data
public class AmazonStoreObject implements StoreObject {

    private final S3Object object;

    @Override
    public InputStream getInputStream() {
        return object.getObjectContent();
    }
}
