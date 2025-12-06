package com.example.jspecify.nullaway.web;

import java.util.Map;
import java.util.Set;

import com.example.jspecify.nullaway.service.UserService;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getUser(@PathVariable long id) {
        String userName = userService.findUserById(id);

        // comment to show jspecify work
        if (userName == null) {
            return ResponseEntity.notFound().build();
        }

        // userName is known to be non-null here
        return ResponseEntity.ok(
                Map.of(
                        "id", String.valueOf(id),
                        "name", userName.toUpperCase()));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@RequestBody CreateUserRequest request) {
        // request.name() is non-null, safe to pass directly
        String createdName = userService.createUser(request.id(), request.name());

        return ResponseEntity.ok(
                Map.of(
                        "id",
                        String.valueOf(request.id()),
                        "name",
                        createdName,
                        "message",
                        "User created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateUser(
            @PathVariable long id, @RequestBody UpdateUserRequest request) {

        boolean updated = userService.updateUser(id, request.newName());

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        String newName = request.newName();
        String message = newName != null ? "User updated to: " + newName : "User removed";

        return ResponseEntity.ok(Map.of("id", String.valueOf(id), "message", message));
    }

    @GetMapping("/{id}/with-default")
    public ResponseEntity<Map<String, String>> getUserWithDefault(
            @PathVariable long id,
            @RequestParam(defaultValue = "Unknown User") String defaultName) {

        // This method returns non-null because service uses defaultName
        String userName = userService.getUserOrDefault(id, defaultName);

        return ResponseEntity.ok(Map.of("id", String.valueOf(id), "name", userName));
    }

    @GetMapping("/{id}/name-length")
    public ResponseEntity<Map<String, Object>> getUserNameLength(@PathVariable long id) {
        int length = userService.getUserNameLength(id);

        if (length == -1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                Map.of(
                        "id", id,
                        "nameLength", length));
    }

    @GetMapping("/ids")
    public ResponseEntity<Set<Long>> getAllUserIds() {
        // userService.getAllUserIds() is guaranteed non-null
        Set<Long> ids = userService.getAllUserIds();
        return ResponseEntity.ok(ids);
    }

    public record CreateUserRequest(long id, String name) {}

    public record UpdateUserRequest(@Nullable String newName) {}
}
