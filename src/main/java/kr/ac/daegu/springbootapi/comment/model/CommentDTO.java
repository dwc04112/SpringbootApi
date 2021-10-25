package kr.ac.daegu.springbootapi.comment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private int cid;
    private int id;
    private String author;
    private String content;
    private Date writeDate;
    private Time writeTime;

}
