package com.project.se.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentDTO{
    @NotBlank
    private String comment;

    @NotBlank
    private int estate_id;
}
