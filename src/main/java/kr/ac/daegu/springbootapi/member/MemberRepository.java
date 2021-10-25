package kr.ac.daegu.springbootapi.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
