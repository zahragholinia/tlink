package ir.tinyLink.model.dto;

import ir.tinyLink.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements Serializable {
    @NotBlank(message = "نام کاربری را وارد کنید.")
    @Pattern(regexp = "^[A-Za-z]\\w{5,29}$", message = "نام کاربری معتبر نمی باشد.")
    private String username;
    @NotBlank(message = "رمز عبور را وارد کنید.")
    @ValidPassword(message = "رمز عبور نامعتبر است.")
    private String password;
}
