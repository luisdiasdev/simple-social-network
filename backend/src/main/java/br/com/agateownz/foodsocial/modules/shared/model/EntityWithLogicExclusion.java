package br.com.agateownz.foodsocial.modules.shared.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class EntityWithLogicExclusion extends EntityWithTimestamp {

    @Column(nullable = false)
    private boolean active;
}
