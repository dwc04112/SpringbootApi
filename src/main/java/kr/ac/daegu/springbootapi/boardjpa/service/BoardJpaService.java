package kr.ac.daegu.springbootapi.boardjpa.service;

import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.boardjpa.model.Board;
import kr.ac.daegu.springbootapi.boardjpa.model.BoardRepository;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardJpaService {

    public final BoardRepository boardRepository;

    public Page<Board> getBoardList(int page, int size) {
        // 숙제 2 : jpa queryMethod를 수정하여 isDel이 "N"인 데이터row들만 나오도록 수정
        PageRequest pageRequest = PageRequest.of(page, size);
        return boardRepository.findBoardsByIsDel("N", pageRequest);
    }

    public Board getBoardById(Integer id) {
        Optional<Board> board = boardRepository.findBoardById(id);
        // 아래 코드를 한줄로 줄이면...
//        if(board.isPresent()){
//            return board.get();
//        } else {
//            return null;
//        }
        return board.orElse(null);
    }

    public Board postBoard(BoardDTO boardDTO) {
        // 답글 작성할 수 있을려면 java단에서 id값을 정한 뒤 save해야 하겠다.
        // jpa로 max(id) + 1 하여 만든 id, replyRootId 함께 set하여 save돌려야 할듯.
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

    public ApiResponse<BoardDTO> updateIsDelBoardById(int id, String boardPassword) {
        // JDK 1.8 Optional에 관해 찾아볼것.
        Optional<Board> boardData = boardRepository.findBoardById(id);
        // 위 boardData가 null 이면 RuntimeException 발생시키고 메소드 종료.
        Board data = boardData.orElseThrow(() -> new RuntimeException("no data"));
        // password 비교
        if(data.getPassword().equals(boardPassword)){
            data.setIsDel("Y");
            boardRepository.save(data); // JPA는 INSERT나 UPDATE 같이 save()를 호출한다.
            return new ApiResponse(true, "board id " + id + " is successfully deleted");
        } else {
            return new ApiResponse(false, "failed to delete board id " + id);
        }
    }

    @Transactional
    public ApiResponse<BoardDTO> postReply(BoardDTO dto) {
        /* JPQL TEST 겸 원글 불러오기 */
        log.debug("log :::: test :: "+dto.getId());
        Board b = boardRepository.selectBoard(dto.getId());
        if(b == null){
            return new ApiResponse(false, "board id " + dto.getReplyRootId() + " is null");
        }
        log.debug(b.toString());

        /* depth와 orderNum을 정하는 로직 START */
        int replyRootId = dto.getReplyRootId();
        int depth = dto.getDepth();
        int orderNum = dto.getOrderNum();

        Integer minOrderNum = boardRepository.getMinOrderNum(replyRootId, depth, orderNum);
        if(minOrderNum == null) {
            minOrderNum = 0;
        }
        log.debug("minOrderNum==" + minOrderNum);
        // minOrderNum이 0인 경우 : root글에 달린 답글들 사이에 추가되는 답글인지? 바로추가답글 : 사이답글임.
        if(minOrderNum == 0) {
            log.debug("======root글에 달린 답글들 사이에 추가되는 답글이 아님(바로추가답글)======");
            orderNum = boardRepository.getReplyOrderNum(replyRootId);
        } else {
            log.debug("======root글에 달린 답글들 사이에 추가되는 답글.(사이답글)======");
            boardRepository.updateOrderNum(replyRootId, minOrderNum);
            orderNum = minOrderNum;
        }
        int newDepth = depth + 1;
        log.debug("newDepth=" + newDepth);
        String newSubject = appendPrefixString("RE : ", depth, dto.getSubject());
        /* depth와 orderNum을 정하는 로직 END */

        /* 새로운 답글 컨텐츠 추가 */
        Board newB = Board.builder()
                .subject(newSubject)
                .author(dto.getAuthor())
                .content(dto.getContent())
                .password(dto.getPassword())
                .replyRootId(replyRootId)
                .writeTime(LocalTime.now())
                .writeDate(LocalDate.now())
                .commentCount(0)
                .readCount(0)
                .isDel("N")
                .depth(newDepth)
                .orderNum(orderNum)
                .build();
        boardRepository.save(newB);

        /* 추가한 답글 컨텐츠 결과 리턴 */
        // JPA를 사용함에 있어 Entity 객체와 DTO객체를 일부러 분리한 이유, Entity <-> DTO의 변환 참고
        // https://dbbymoon.tistory.com/4
        dto.setSubject(newSubject);
        dto.setReplyRootId(replyRootId);
        dto.setDepth(newDepth);
        dto.setOrderNum(orderNum);
        return new ApiResponse(true, dto);
    }

    // 아래 메소드의 뜻을 한국어로 번역 해 보시오.
    private String appendPrefixString(String appendPrefix, int loop, String target) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<=loop; i++){
            builder.append(appendPrefix);
        }
        builder.append(target);
        return builder.toString();
    }
}