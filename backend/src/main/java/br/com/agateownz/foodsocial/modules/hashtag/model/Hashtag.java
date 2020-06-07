package br.com.agateownz.foodsocial.modules.hashtag.model;

import br.com.agateownz.foodsocial.modules.shared.model.EntityWithTimestamp;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
