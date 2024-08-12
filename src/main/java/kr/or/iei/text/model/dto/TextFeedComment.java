package kr.or.iei.text.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TextFeedComment {
	private int textFeedCommentNo;
	private String textFeedCommentContent;
	private String textFeedCommentWriter;
	private String textFeedCommentRegDate;
	private int textFeedCommentRef;
	private int isLike;
	private String textFeedCommentWriterImg;
	private int likeCount;
}
