package kr.ac.daegu.springbootapi.boardjpa.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "board")
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "author")
    private String author;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;

    @Column(name = "writeDate")
    private LocalDate writeDate;
    @Column(name = "writeTime")
    private LocalTime writeTime;
    @Column(name = "readCount")
    private Integer readCount;
    @Column(name = "commentCount")
    private Integer commentCount;
    @Column(name = "password")
    private String password;
    @Column(name = "replyRootId")
    private Integer replyRootId;
    @Column(name = "depth")
    private Integer depth;
    @Column(name = "orderNum")
    private Integer orderNum;
    @Column(name = "isDel")
    private String isDel;

    public Board(Integer id,
                 String author,
                 String subject,
                 String content,
                 LocalDate writeDate,
                 LocalTime writeTime,
                 Integer readCount,
                 Integer commentCount,
                 String password,
                 Integer replyRootId,
                 Integer depth,
                 Integer orderNum,
                 String isDel) {
        this.id = id;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.writeDate = writeDate;
        this.writeTime = writeTime;
        this.readCount = readCount;
        this.commentCount = commentCount;
        this.password = password;
        this.replyRootId = replyRootId;
        this.depth = depth;
        this.orderNum = orderNum;
        this.isDel = isDel;
    }
}