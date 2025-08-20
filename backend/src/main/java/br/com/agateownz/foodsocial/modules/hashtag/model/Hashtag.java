package br.com.agateownz.foodsocial.modules.hashtag.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "fs_hashtag")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class Hashtag extends EntityWithTimestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hashtag_seq_gen")
    @SequenceGenerator(name = "hashtag_seq_gen", sequenceName = "hashtag_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String hashtag;

    @Column(nullable = false)
    private LocalDateTime lastUsed;

    public Hashtag updateLastUsed() {
        this.lastUsed = LocalDateTime.now();
        return this;
    }
}
