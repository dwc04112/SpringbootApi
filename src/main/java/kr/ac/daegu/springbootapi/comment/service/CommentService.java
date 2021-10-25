package kr.ac.daegu.springbootapi.comment.service;

import kr.ac.daegu.springbootapi.board.model.BoardDAO;
import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.comment.model.CommentDAO;
import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService {

    public final BoardDAO boardDAO;
    public final CommentDAO commentDAO;

    public ApiResponse<CommentDTO> postComment(CommentDTO commentDTO) throws Exception {
        // 요청받은 id값에 해당하는 Board컨텐츠가 있는지 없는지?
        BoardDTO selectedBoard = boardDAO.getBoardById(commentDTO.getId());
        if(selectedBoard == null){
            String failedMessage = "id value " + commentDTO.getId() + " is not exists in board";
            return new ApiResponse<>(false, failedMessage);
        } else {
            commentDAO.postComment(commentDTO);
            String successMessage = "success to insert comment data id " + commentDTO.getId();
            return new ApiResponse<>(true, successMessage, commentDTO);
        }
    }

    public ApiResponse<CommentDTO> getCommentsByBoardId(int boardId) {
        // 요청받은 boardId가 가진 comment테이블의 데이터 목록
        List<CommentDTO> comments = commentDAO.getCommentsByBoardId(boardId);
        return new ApiResponse(true, "success to get comments by boardId" + boardId, comments);
    }
}
