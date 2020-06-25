package com.project.se.service;

import com.project.se.domain.ApplicationUser;
import com.project.se.domain.Comment;
import com.project.se.dto.CommentDTO;
import com.project.se.repository.CommentRepository;
import com.project.se.repository.UserRepository;
import com.project.se.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EstateSocketService estateSocketService;

    public Comment postComment(CommentDTO commentDTO, String token){
        Comment comment = new Comment();
        ZonedDateTime now = ZonedDateTime.now();
        String jwt = token.substring(7);
        int user_id = jwtTokenProvider.getUserIdFromJWT(jwt);
        try{
            ApplicationUser applicationUser = userRepository.findById(user_id).orElseThrow(() ->
                new EntityNotFoundException("Not found"));
            String userName = applicationUser.getName();
            comment.setComment(commentDTO.getComment());
            comment.setEstate_id(commentDTO.getEstate_id());
            comment.setCreated_at(now);
            comment.setUser_id(user_id);
            comment.setName(userName);
            commentRepository.save(comment);
            estateSocketService.sendComment(commentDTO.getEstate_id());
            return comment;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Comment> getCommentForEstate(int id){
        List<Comment> commentList = commentRepository.findByEstate_id(id);
        return commentList;
    }
}
