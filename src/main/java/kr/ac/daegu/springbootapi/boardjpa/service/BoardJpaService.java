package kr.ac.daegu.springbootapi.boardjpa.service;

import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.boardjpa.model.Board;
import kr.ac.daegu.springbootapi.boardjpa.model.BoardRepository;
import kr.ac.daegu.springbootapi.comment.model.CommentDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardJpaService {

    public final BoardRepository boardRepository;

    public List<Board> getBoardList() {
        // 숙제 2 : jpa queryMethod를 수정하여 isDel이 "N"인 데이터row들만 나오도록 수정
        return boardRepository.findAll();
    }

    public Board getBoardById(Integer id) {
        return boardRepository.findBoardById(id);
    }

    public Board postBoard(BoardDTO boardDTO) {
        Board postData = Board.builder()
                .author(boardDTO.getAuthor())
                .subject(boardDTO.getSubject())
                .content(boardDTO.getContent())
                .password(boardDTO.getPassword())
                .commentCount(0)
                .depth(0)
                .orderNum(0)
                .isDel("N")
                .readCount(0)
                .replyRootId(0)
                .writeDate(LocalDate.now())
                .writeTime(LocalTime.now())
                .build();
        return boardRepository.save(postData);
    }

    public Board putBoard(int id, BoardDTO boardDTO) {
        Optional<Board> boardData = boardRepository.findBoardById(id);

        // 람다식을 사용하여
        boardData.ifPresent(selectedBoard -> {
            selectedBoard.setAuthor(boardDTO.getAuthor());
            selectedBoard.setSubject(boardDTO.getSubject());
            selectedBoard.setContent(boardDTO.getContent());
            selectedBoard.setPassword(boardDTO.getPassword());
            boardRepository.save(selectedBoard);
        });

        return boardData.orElseGet(boardData::get);
    }
}