package br.com.agateownz.foodsocial.modules.content.dto.response;

import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import lombok.Data;

@Data
public class InternalContentResponse {
    private final String contentType;
    private final Long contentLength;
    private final StoreObject storeObject;
}
