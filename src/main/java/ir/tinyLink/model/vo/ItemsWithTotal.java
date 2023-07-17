package ir.tinyLink.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemsWithTotal<T> {
    @Builder.Default
    private List<T> items = new ArrayList<>();
    @Builder.Default
    private Long total = 0L;
}
