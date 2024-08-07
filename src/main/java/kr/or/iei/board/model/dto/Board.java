package kr.or.iei.board.model.dto;



import java.util.List;

import kr.or.iei.user.model.dto.UserImg;
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
	 
	
	//유저피드
	private int userFeedNo;
	private String userFeedWriter;
	private String userFeedContnet;
	private String userFeedDate;
	private int userFeedCount;
	
	//좋아요 컬럼
	private int photoFeedLikeNo;
	private String photoFeedLikeWriter;
	private int totalLikes;
	
	//유저정보
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private String userNick;
	private String userInfo;
	private String userEmail;
	private int userLevel;
	private int warningCount;
	private String createDate;
	private String updateDate;
	private List<UserImg> imgList;
	
}
