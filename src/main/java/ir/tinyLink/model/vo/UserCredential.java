package ir.tinyLink.model.vo;

import ir.tinyLink.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by z.gholinia on 2020/07/28 @PodBusinessPanel.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential implements Serializable {
    private long id;
    private String username;
    private String password;
}
