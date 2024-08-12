package kr.or.iei.feed.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedComment {
	private int feedCommentNo; //댓글 관리 번호, 시퀀스로 관리
	private String feedCommentContent; //댓글이 달린 해당 게시물 내용
	private String feedCommentDate; //댓글 작성일
	private int feedRef; //댓글이 달린 글의 피드게시물 넘버
	private String feedCommentWriter;//글쓴 유저
	private String userId;
	private String userImg;
	private int isLike;
}
