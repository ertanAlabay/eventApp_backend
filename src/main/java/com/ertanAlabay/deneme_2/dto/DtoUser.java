package com.ertanAlabay.deneme_2.dto;

import com.ertanAlabay.deneme_2.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
