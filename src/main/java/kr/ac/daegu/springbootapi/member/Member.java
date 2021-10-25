package kr.ac.daegu.springbootapi.member;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member createMember(String email, String password){
        return new Member(email, password);
    }
}
