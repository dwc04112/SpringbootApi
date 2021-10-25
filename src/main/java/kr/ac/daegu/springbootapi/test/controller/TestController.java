package kr.ac.daegu.springbootapi.test.controller;

import kr.ac.daegu.springbootapi.common.ApiResponse;
import kr.ac.daegu.springbootapi.test.model.TestDTO;
import kr.ac.daegu.springbootapi.test.service.TestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    public final TestService testService;

    @GetMapping(value = "/")
    public ApiResponse<TestDTO> rootTest() throws Exception {
        log.trace("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~"); // 로그의 5단계 trace(일반적으로 남기는 이벤트 기록) -> debug(디버깅용) -> info(알림) -> warn(경고) -> error(에러)
        log.debug("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.info("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.warn("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        log.error("logTest~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        // 간단한 list 생성
        List<TestDTO> testList = testService.getTestList();

        return new ApiResponse(true, testList);
    }

    @PostMapping(value = "/")
    public String insertTest(@RequestBody TestDTO testDTO) throws Exception {
        log.debug("insertTest");
        return testService.insertTest(testDTO);
    }

    @GetMapping(value = "/dtoInList")
    public ApiResponse<DtoInList> dtoInList() throws Exception {
        List<TestListDTO> list = new ArrayList<TestListDTO>();
        list.add(new TestListDTO("name1", 123));
        list.add(new TestListDTO("name2", 2222));
        DtoInList dtoInList = new DtoInList("hihi", 1234, "llll", list);
        return new ApiResponse(true, dtoInList);
    }
}

@Data
@AllArgsConstructor
class DtoInList {
    private String name;
    private int num;
    private String name2;
    private List<TestListDTO> list;
}

@Data
@AllArgsConstructor
class TestListDTO{
    private String name;
    private int number;
}
