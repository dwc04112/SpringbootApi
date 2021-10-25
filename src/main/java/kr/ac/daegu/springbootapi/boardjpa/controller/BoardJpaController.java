package kr.ac.daegu.springbootapi.boardjpa.controller;

import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.boardjpa.model.Board;
import kr.ac.daegu.springbootapi.boardjpa.service.BoardJpaService;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/boardjpa")
public class BoardJpaController {

    public final BoardJpaService boardJpaService;

    @GetMapping(value = "/")
    public ApiResponse<BoardDTO> getBoardList(){
        List<Board> list = boardJpaService.getBoardList();
        return new ApiResponse(true, list);
    }

    @GetMapping(value = "/{id}") // PathVariable
    public ApiResponse<BoardDTO> getBoardById(@PathVariable Integer id){
        Board data = boardJpaService.getBoardById(id);
        return new ApiResponse(true, data);
    }

    @PostMapping(value = "/")
    public ApiResponse<BoardDTO> postBoard(BoardDTO boardDTO){
        Board data = boardJpaService.postBoard(boardDTO);
        return new ApiResponse(true, data);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<BoardDTO> putBoard(@PathVariable int id,
                                          @RequestBody BoardDTO boardDTO){
        Board data = boardJpaService.putBoard(id, boardDTO);
        return new ApiResponse(true, data);
    }

    // DELETE /boardjpa/{id} 구현할것.
    // logic : board.controller.BoardController의 로직 따를것
    //         무슨말이냐면 DB에 데이터를 DELETE 시키지 말고, board 컬럼 중 isDel 을 "Y"로 업데이트.

}