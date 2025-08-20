package br.com.agateownz.foodsocial.modules.shared.service.storage;

import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import java.io.InputStream;
import lombok.Data;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Data
public class AmazonStoreObject implements StoreObject {

    private final ResponseInputStream<GetObjectResponse> object;

    @Override
    public InputStream getInputStream() {
        return object;
    }
}
