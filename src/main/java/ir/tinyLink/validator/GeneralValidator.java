package ir.tinyLink.validator;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * Configuration for hibernate
 *
 * @author Zahra Gholinia
 * @since 2023-07-12
 */

public class GeneralValidator {

    /**
     * Configuration for hibernate
     *
     * @author Zahra Gholinia
     * @since 2023-07-12
     */
    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(@Nullable CharSequence string) {
        return string == null || string.length() == 0;
    }

    /**
     * Configuration for hibernate
     *
     * @author Zahra Gholinia
     * @since 2023-07-12
     */
    @Contract(value = "null -> false", pure = true)
    public static boolean isNotEmpty(@Nullable CharSequence string) {
        return string != null && string.length() != 0;
    }

    /**
     * Configuration for hibernate
     *
     * @author Zahra Gholinia
     * @since 2023-07-12
     */
    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Configuration for hibernate
     *
     * @author Zahra Gholinia
     * @since 2023-07-12
     */
    @Contract(value = "null -> false", pure = true)
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }


    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }


    @Contract(value = "null -> false", pure = true)
    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }


    @Contract(value = "null -> true", pure = true)
    public static boolean isEmpty(@Nullable JsonArray jsonArray) {
        return jsonArray == null || jsonArray.size() == 0;
    }


    @Contract(value = "null -> false", pure = true)
    public static boolean isNotEmpty(@Nullable JsonArray jsonArray) {
        return jsonArray != null && jsonArray.size() > 0;
    }
}
