
package kr.ac.daegu.springbootapi.boardjpa.controller;

import kr.ac.daegu.springbootapi.board.model.BoardDTO;
import kr.ac.daegu.springbootapi.boardjpa.model.Board;
import kr.ac.daegu.springbootapi.boardjpa.service.BoardJapService;
import kr.ac.daegu.springbootapi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/boardjpa")
public class BoardJpaController {

    public final BoardJapService boardJpaService;

    @GetMapping(value = "/")
    public ApiResponse<BoardDTO> getBoardList(){
        List<Board> list = boardJpaService.getBoardList();
        return new ApiResponse(true, list);
    }

}