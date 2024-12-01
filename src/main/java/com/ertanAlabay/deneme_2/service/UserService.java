package com.ertanAlabay.deneme_2.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ertanAlabay.deneme_2.dto.DtoLogin;
import com.ertanAlabay.deneme_2.dto.DtoRegister;
import com.ertanAlabay.deneme_2.dto.DtoUser;
import com.ertanAlabay.deneme_2.model.Role;
import com.ertanAlabay.deneme_2.model.User;
import com.ertanAlabay.deneme_2.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(DtoRegister registerDTO) {
        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(Role.USER); // Default rol
        userRepository.save(user);
    }

    public String loginUser(DtoLogin loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        // JWT Token oluşturulacak
        return "JWT_TOKEN";
    }

    public List<DtoUser> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                	DtoUser userDTO = new DtoUser();
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setEmail(user.getEmail());
                    userDTO.setRole(user.getRole());
                    return userDTO;
                }).collect(Collectors.toList());
    }

    public void updateUser(Long id, DtoUser userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}