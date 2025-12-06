package com.example.jspecify.nullaway.service;

import java.util.HashMap;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final Map<Long, String> userDatabase = new HashMap<>();

    public UserService() {
        userDatabase.put(1L, "Alice");
        userDatabase.put(2L, "Bob");
        userDatabase.put(3L, "Charlie");
    }

    public @Nullable String findUserById(long userId) {
        return userDatabase.get(userId);
    }

    public String createUser(long userId, String name) {
        userDatabase.put(userId, name);
        return name;
    }

    public boolean updateUser(long userId, @Nullable String newName) {
        if (!userDatabase.containsKey(userId)) {
            return false;
        }

        if (newName == null) {
            userDatabase.remove(userId);
        } else {
            userDatabase.put(userId, newName);
        }
        return true;
    }

    public String getUserOrDefault(long userId, String defaultName) {
        String name = findUserById(userId);
        // NullAway knows name can be null, so we must check before returning
        return name != null ? name : defaultName;
    }

    public int getUserNameLength(long userId) {
        String name = findUserById(userId);
        if (name == null) {
            return -1;
        }
        // NullAway knows name is non-null here after the check
        return name.length();
    }

    /**
     * Example of what NullAway would catch: Uncommenting this method would cause a compile error
     * because we're dereferencing a potentially null value without checking.
     */
    /*
    public int getBadUserNameLength(long userId) {
        String name = findUserById(userId);
        // ERROR: NullAway would flag this - dereferencing nullable without null check
        return name.length();
    }
    */

    public java.util.Set<Long> getAllUserIds() {
        return userDatabase.keySet();
    }
}
