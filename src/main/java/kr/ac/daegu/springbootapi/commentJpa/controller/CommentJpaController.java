
package kr.ac.daegu.springbootapi.commentJpa.controller;


import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.commentJpa.model.Comment;
import kr.ac.daegu.springbootapi.commentJpa.service.CommentJpaService;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/commentjpa")
public class CommentJpaController {

    public final CommentJpaService commentJpaService;

    @PostMapping(value = "/")
    public ApiResponse<CommentDTO> postComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO data = commentJpaService.postComment(commentDTO);
        return new ApiResponse(true, data);
    }
}