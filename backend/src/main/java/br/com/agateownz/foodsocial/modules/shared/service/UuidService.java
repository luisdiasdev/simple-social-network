package br.com.agateownz.foodsocial.modules.shared.service;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UuidService {

    public String getRandomUuuid() {
        return UUID.randomUUID().toString();
    }
}
