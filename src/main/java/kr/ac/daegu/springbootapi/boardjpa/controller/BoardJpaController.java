package kr.ac.daegu.springbootapi.boardjpa.controller;

import kr.ac.daegu.springbootapi.board.model.BoardDTO;
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

    public final BoardJapService boardJapService;

    @GetMapping(value = "/")
    public ApiResponse<BoardDTO> getBoardList(){
        List<BoardDTO> list = boardJapService.getBoardList();
        return new ApiResponse(true, list);
    }

    @PostMapping(value = "/")
    public ApiResponse<BoardDTO> postBoard(@RequestBody BoardDTO boardDTO) throws Exception {
        BoardDTO dto = boardJapService.postBoard(boardDTO);
        return new ApiResponse(true, dto);
    }

    @PutMapping(value = "/{id}")
    public ApiResponse<?> putBoard(@PathVariable int id,
                           @RequestBody BoardDTO boardDTO) throws Exception {
        log.debug("id: " + id);
        return boardJapService.putBoard(id, boardDTO);
    }

    /* mission */
    // 글 읽기
    // 요청URL은 GET http://localhost:8080/board/{id}
    @GetMapping(value = "/{id}")
    public ApiResponse<BoardDTO> getBoardById(@PathVariable int id) throws Exception {
        return boardJapService.getBoardById(id);
    }
    
    // 글 삭제
    // isDeleted : Y로 업데이트 시킴.
    // 요청URL은 DELETE http://localhost:8080/board/{id}
    @DeleteMapping(value = "/{id}")
    public ApiResponse<BoardDTO> updateIsDelBoardById(@PathVariable int id,
                                                      @RequestBody BoardDTO boardDTO) throws Exception {
        String boardPassword = boardDTO.getPassword();
        log.debug("request.id=" + id);
        log.debug("request.password=" + boardPassword);
        // password가 없을 경우
        if(boardPassword == null){
            return new ApiResponse<>(false, "boardPassword is null, please check key name 'password'", null);
        }
        return boardJapService.updateIsDelBoardById(id, boardPassword);
    }

}
