
package kr.ac.daegu.springbootapi.boardjpa.service;

import kr.ac.daegu.springbootapi.boardjpa.model.Board;
import kr.ac.daegu.springbootapi.boardjpa.model.BoardRepository;
import kr.ac.daegu.springbootapi.comment.model.CommentDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardJapService {

    public final BoardRepository boardRepository;
    public final CommentDAO commentDAO;

    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

}