package kr.ac.daegu.springbootapi.comment.model;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO {

    int postComment(CommentDTO commentDTO);

    List<CommentDTO> getCommentsByBoardId(int boardId);
}
