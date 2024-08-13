package kr.or.iei.photo.model.dto;

import java.util.List;

import kr.or.iei.text.model.dto.TextFeedComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo {
	private int photoFeedNo;
	private String photoFeedImg;
	private String photoFeedWriter;
	private int readCount;
	private String regDate;
	private List<PhotoComment> photoCommentList;
	private int isLike;
	private int likeCount;
	private int isDec;
	private int isSave;
	private int userNo;
	private int warningCount;
	private String photoFeedWriterImg;
	
}
