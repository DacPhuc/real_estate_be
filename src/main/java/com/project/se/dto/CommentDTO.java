package com.project.se.dto;

import javax.validation.constraints.NotBlank;

public class CommentDTO{
    @NotBlank
    private String comment;

    @NotBlank
    private int estate_id;
}
