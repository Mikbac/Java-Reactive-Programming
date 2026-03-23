package pl.mikbac.example200.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by MikBac on 17.03.2026
 */
public record UserWithPriority(String username, Category category) {

    @AllArgsConstructor
    @Getter
    public enum Category {
        PRIME(1),
        GUEST(2);

       private final int priority;
    }

}
