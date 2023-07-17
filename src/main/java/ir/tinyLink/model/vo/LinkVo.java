package ir.tinyLink.model.vo;

import ir.tinyLink.model.entity.LinkEntity;
import ir.tinyLink.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    private String shortLink;

    private long id;


    private String longLink;

    private Date updatedAt;

    private UserEntity user;

    private int viewCount;
}
