package ir.tinyLink.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Entity
@Table(name = "links")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(name="longlink")
    private String longLink;

    @UpdateTimestamp
    @Column(name="updatedat")
    private Date updatedAt;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "fk_user_id",nullable=false)
    private UserEntity user;

    @Column(name="viewcount")
    private int viewCount;
}
