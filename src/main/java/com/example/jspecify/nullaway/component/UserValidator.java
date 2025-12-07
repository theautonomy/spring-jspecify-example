package com.example.jspecify.nullaway.component;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

@NullMarked
@Component
public class UserValidator {

    public boolean isValidUser(@Nullable User user) {
        // Comment line 13 to 15 to see different behavior
        if (user == null) {
            return false;
        }

        String name = user.name();
        String email = user.email();

        return name != null && !name.isBlank() && email != null && !email.isBlank();
    }

    public @Nullable String normalizeEmail(@Nullable String email) {
        if (email == null) {
            return null;
        }
        return email.toLowerCase().trim();
    }
}
