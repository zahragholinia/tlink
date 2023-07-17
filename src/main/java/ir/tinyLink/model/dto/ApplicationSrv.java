package ir.tinyLink.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class ApplicationSrv {
    private String token;
}
