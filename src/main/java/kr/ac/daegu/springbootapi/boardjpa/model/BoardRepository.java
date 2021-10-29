package kr.ac.daegu.springbootapi.boardjpa.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAll();
    Page<Board> findBoardsByIsDel(String isDel, Pageable pageable);
    Optional<Board> findBoardById(int id);

    @Query("Select b FROM Board b where b.id = ?1") // JPA를 이용하여 쿼리를 날린다.
    Board selectBoard(int id);

    @Query("SELECT MIN(b.orderNum) FROM Board b" +
            "                WHERE  b.replyRootId = ?1" +
            "                AND b.orderNum > ?3" +
            "                AND b.depth <= ?2")
    default Integer getMinOrderNum(int replyRootId, int depth, int orderNum) {
        return 0; // 인터페이스 결과가 null 일경우 default value 지정. (JDK 8 기능)
    }

    @Query("SELECT MAX(orderNum) + 1 FROM Board" +
            " WHERE replyRootId = ?1")
    default Integer getReplyOrderNum(int replyRootId) {
        return 0; // 인터페이스 결과가 null 일경우 default value 지정. (JDK 8 기능)
    };

    @Query("UPDATE Board SET orderNum = orderNum + 1" +
            "                WHERE replyRootId = ?1  AND orderNum >= ?2")
    void updateOrderNum(int replyRootId, int minOrderNum);

}