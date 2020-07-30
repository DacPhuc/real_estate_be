package com.project.se.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
