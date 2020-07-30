package com.project.se.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String comment;
    private int user_id;
    private int estate_id;
    private String name;
    private ZonedDateTime created_at;
}
