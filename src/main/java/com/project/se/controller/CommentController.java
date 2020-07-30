package com.project.se.controller;

import com.project.se.domain.Comment;
import com.project.se.dto.CommentDTO;
import com.project.se.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/estate/comment")
    public ResponseEntity<?> postComment(@RequestBody CommentDTO commentDTO, @RequestHeader(name = "Authorization") String token){
        Comment result = commentService.postComment(commentDTO, token);
        if (result == null){
            return new ResponseEntity<>("Failed to create comment", HttpStatus.FAILED_DEPENDENCY);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/estate/comment")
    public ResponseEntity<?> getComment(@RequestParam int estate_id){
        List<Comment> commentList = commentService.getCommentForEstate(estate_id);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
}
