package com.project.se.controller;

import com.project.se.dto.CommentDTO;
import com.project.se.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/estate/comment")
    public ResponseEntity<?> postComment(@RequestBody CommentDTO commentDTO, @RequestHeader(name = "Authorization") String token){
        System.out.println(commentDTO);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
