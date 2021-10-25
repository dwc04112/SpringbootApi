
package kr.ac.daegu.springbootapi.boardjpa.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {
    List<Board> findAll();
    Board findBoardById(Integer id);
    Optional<Board> findBoardById(int id);
}