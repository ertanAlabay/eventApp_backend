package com.ertanAlabay.deneme_2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ertanAlabay.deneme_2.dto.DtoLogin;
import com.ertanAlabay.deneme_2.dto.DtoRegister;
import com.ertanAlabay.deneme_2.dto.DtoUser;
import com.ertanAlabay.deneme_2.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody DtoRegister registerDTO) {
        userService.registerUser(registerDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody DtoLogin loginDTO) {
        String token = userService.loginUser(loginDTO);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<List<DtoUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody DtoUser userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
