package kr.ac.daegu.springbootapi.comment.controller;

import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.board.service.BoardService;
import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.comment.service.CommentService;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/comment")
public class CommentController {

    public final CommentService commentService;

    @PostMapping(value = "/")
    public ApiResponse<CommentDTO> postComment(@RequestBody CommentDTO commentDTO) throws Exception {
        return commentService.postComment(commentDTO);
    }

    @GetMapping(value = "/{boardId}")
    public ApiResponse<CommentDTO> getCommentsByBoardId(@PathVariable int boardId) throws Exception {
        return commentService.getCommentsByBoardId(boardId);
    }

}
