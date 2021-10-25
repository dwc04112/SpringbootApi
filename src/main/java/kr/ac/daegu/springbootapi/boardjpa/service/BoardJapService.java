package kr.ac.daegu.springbootapi.boardjpa.service;

import kr.ac.daegu.springbootapi.board.model.BoardDAO;
import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.comment.model.CommentDAO;
import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardJapService {

    public final BoardDAO boardDAO;
    public final CommentDAO commentDAO;

    public List<BoardDTO> getBoardList() {
        return boardDAO.getBoardList();
    }

    public BoardDTO postBoard(BoardDTO boardDTO) throws Exception {
        log.debug(boardDTO.toString());

        // Inserted Date, Inserted Time 정의
        boardDTO.setWriteDate(LocalDate.now());
        boardDTO.setWriteTime(LocalTime.now());

        int insertedRowCount = boardDAO.postBoard(boardDTO);
        if(insertedRowCount > 0){
            return boardDTO;
        } else {
            throw new Exception("failed to insert board data");
        }
    }

    public ApiResponse<?> putBoard(int id, BoardDTO boardDTO) throws Exception {
        // board의 비번 검사
        String boardPassword = boardDAO.getBoardById(id).getPassword();
        if(isBoardPasswordMisMatch(boardDTO, boardPassword)){
            return new ApiResponse<>(false, "board password is not match");
        }

        boardDTO.setId(id);
        boardDTO.setWriteDate(LocalDate.now());
        boardDTO.setWriteTime(LocalTime.now());
        int result = boardDAO.putBoard(boardDTO);

        if(result > 0){
            return new ApiResponse<>(true, result + " rows updated");
        }
        throw new Exception("failed to update " + id + " content");
    }

    private boolean isBoardPasswordMisMatch(BoardDTO boardDTO, String boardPassword) {
        log.debug("boardPassword="+boardPassword);
        log.debug("requestedPassword="+boardDTO.getPassword());
        boolean isMisMatch = !boardDTO.getPassword().equals(boardPassword);
        log.debug("isMisMatch=" + isMisMatch);
        return isMisMatch;
    }

    public ApiResponse<BoardDTO> getBoardById(int boardId) {
        BoardDTO data = boardDAO.getBoardById(boardId);
        if(isDeletedData(data)){
            return new ApiResponse(false, "boardId " + boardId + " is already deleted");
        }
        List<CommentDTO> commentsById = commentDAO.getCommentsByBoardId(boardId);
        data.setComments(commentsById);
        return new ApiResponse(true, data);
    }

    private boolean isDeletedData(BoardDTO data) {
        return data.getIsDel().equals("Y");
    }

    // Board테이블의 isDel 컬럼의 데이터를 'Y' 로 업데이트
        /*
    * DELETE /board/{id} 고쳐서.
        isDel 컬럼을 업데이트하는
        비즈니스 로직은 그대로 가져감.

        패스워드 일치: json response 아래처럼 나오도록
        {
            “success”: true,
            “message”: “success to delete board id {id}”
        }


        패스워드 일치하지 않으면
        아래 json response 나오도록
        {
            “success”: false,
            “message”: “password incorrect in board id {id}”
            “data”: null
        }
    * */
    public ApiResponse<BoardDTO> updateIsDelBoardById(int id, String boardPassword) {
        // 1. 비번 체크
        BoardDTO data = boardDAO.getBoardById(id);
        boolean isPwdMatch = data.getPassword().equals(boardPassword);
        // 2. 틀리면 예외 메세지 리턴
        if(!isPwdMatch) {
            return new ApiResponse<>(false, "board password is not match, please check requested board password");
        }
        // 3. 맞으면 isDel업데이트
        int updatedRow = boardDAO.updateIsDelBoardById(id);
        if(updatedRow > 0) {
            return new ApiResponse(true, "board id " + id + " is successfully deleted");
        }
        return new ApiResponse(false, "failed to delete board id " + id);
    }
}
