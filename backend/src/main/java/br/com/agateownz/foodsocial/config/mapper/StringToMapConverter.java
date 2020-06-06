package br.com.agateownz.foodsocial.config.mapper;

import java.util.Map;

public interface StringToMapConverter {

    @FromJson
    Map convert(String string);
}
