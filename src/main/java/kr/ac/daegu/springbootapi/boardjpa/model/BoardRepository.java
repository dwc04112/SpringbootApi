
package kr.ac.daegu.springbootapi.boardjpa.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BoardRepository extends CrudRepository<Board, Integer> {
    List<Board> findAll();
}