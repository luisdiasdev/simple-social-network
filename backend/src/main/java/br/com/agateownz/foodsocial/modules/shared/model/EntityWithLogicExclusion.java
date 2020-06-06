package br.com.agateownz.foodsocial.modules.shared.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class EntityWithLogicExclusion extends EntityWithTimestamp {

    @Column(nullable = false)
    private boolean active;
}
