package kr.ac.daegu.springbootapi.board.model;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardDAO {
    int putBoard(BoardDTO boardDTO);

    List<BoardDTO> getBoardList();

    int postBoard(BoardDTO boardDTO);

    BoardDTO getBoardById(int id);

    int updateIsDelBoardById(int id);
}
