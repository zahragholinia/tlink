package ir.tinyLink.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
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
