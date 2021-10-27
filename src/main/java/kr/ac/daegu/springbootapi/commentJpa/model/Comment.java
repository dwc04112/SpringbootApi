package kr.ac.daegu.springbootapi.commentJpa.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private int cid;

    @Column(name = "id")
    private int id;
    @Column(name = "author")
    private String author;
    @Column(name = "content")
    private String content;
    @Column(name = "writeDate")
    private LocalDate writeDate;
    @Column(name = "writeTime")
    private LocalTime writeTime;

    public Comment(int cid, int id, String author, String content, LocalDate writeDate, LocalTime writeTime) {
        this.cid = cid;
        this.id = id;
        this.author = author;
        this.content = content;
        this.writeDate = writeDate;
        this.writeTime = writeTime;
    }
}