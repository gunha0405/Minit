package kr.or.iei.board.model.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
	
	//사진
	private int photoFeedNo;
	private String photoFeedImg;
	private String photoFeedWriter;
	private int readCount;
	private String regDate;
	
	//피드
	private int userFeedNo;
	private String userFeedWriter;
	private String userFeedContnet;
	private String userFeedDate;
	private int userFeedCount;
	private String followingId;
	
	//텍스트
	private int textFeedNo;
	private String textFeedContent;
	private int textFeedReadCount;
	private String textFeedRegDate;
	private String textFeedWriter;
	

	
}
