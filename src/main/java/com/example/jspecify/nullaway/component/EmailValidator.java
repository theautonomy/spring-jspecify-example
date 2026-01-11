package com.example.jspecify.nullaway.component;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

// Uncomment to see behavior
// @NullMarked
@Component
public class EmailValidator {

    public @Nullable String normalizeEmail(@Nullable String email) {
        return email.toLowerCase().trim();
    }
}
