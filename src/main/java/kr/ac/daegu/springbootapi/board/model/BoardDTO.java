package kr.ac.daegu.springbootapi.board.model;

import kr.ac.daegu.springbootapi.comment.model.CommentDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class BoardDTO {
    private int id;
    private String author;
    private String subject;
    private String content;
    private LocalDate writeDate;
    private LocalTime writeTime;
    private int readCount;
    private int commentCount;
    private String password;
    private int replyRootId;
    private int depth;
    private int orderNum;
    private String isDel;

    private List<CommentDTO> comments = null;

    @Override
    public String toString() {
        return "BoardDTO{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", writeDate=" + writeDate +
                ", writeTime=" + writeTime +
                ", readCount=" + readCount +
                ", commentCount=" + commentCount +
                ", password='" + password + '\'' +
                ", replyRootId=" + replyRootId +
                ", depth=" + depth +
                ", orderNum=" + orderNum +
                ", isDel='" + isDel + '\'' +
                ", comments=" + comments +
                '}';
    }
}
