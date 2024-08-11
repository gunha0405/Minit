package kr.or.iei.photo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PhotoComment {
	private int photoFeedCommentNo;
	private String photoFeedCommentWriter;
	private String photoFeedCommentContent;
	private String photoFeedCommentDate;
	private int photoRef;
	private String photoFeedCommentWriterImg;
}
