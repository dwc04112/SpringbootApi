package kr.ac.daegu.springbootapi.commentJpa.service;

import kr.ac.daegu.springbootapi.boardjpa.model.Board;
import kr.ac.daegu.springbootapi.boardjpa.model.BoardRepository;
import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import kr.ac.daegu.springbootapi.commentJpa.model.Comment;
import kr.ac.daegu.springbootapi.commentJpa.model.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentJpaService {

    public final CommentRepository commentRepository;
    public final BoardRepository boardRepository;

    @Transactional
    public CommentDTO postComment(CommentDTO commentDTO) {
        /* DBMS Comment 테이블에 설정되어 있는 제약조건 삭제 후 테스트 해 볼것. */
//        댓글달기 API (POST /commentJpa/) 호출 하면
//        Comment 테이블에 데이터 INSERT
        Comment c = Comment.builder()
                .author(commentDTO.getAuthor())
                .content(commentDTO.getContent())
                .id(commentDTO.getId())
                .writeDate(commentDTO.getWriteDate().toLocalDate())
                .writeTime(commentDTO.getWriteTime().toLocalTime())
                .build();
        commentRepository.save(c);

        log.debug("comment Repository save executed.");

        // Board 테이블 해당 id값의 row 중 readCount를 +1 증가 시키는(UPDATE) 트랜잭션 로직 추가.
        Optional<Board> b = boardRepository.findBoardById(commentDTO.getId());
        Board board = b.orElseThrow(() ->
                new NullPointerException("board id value" + commentDTO.getId() + "is null")
        );
        board.setReadCount(board.getReadCount() + 1);
        boardRepository.save(board);
        return commentDTO;
    }
}