package br.com.agateownz.foodsocial.modules.shared.dto;

import lombok.Data;

@Data(staticConstructor = "of")
public class Message {

    private final String message;
}
