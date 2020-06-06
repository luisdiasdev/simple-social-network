package br.com.agateownz.foodsocial.modules.shared.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidService {

    public String getRandomUuuid() {
        return UUID.randomUUID().toString();
    }
}
