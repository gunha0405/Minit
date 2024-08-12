package kr.or.iei.board.model.dto;



import java.util.List;

import kr.or.iei.feed.model.dto.Feed;
import kr.or.iei.photo.model.dto.Photo;
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
	//private String userId; photoFeedWriter과 같다 .
	private String userPw;
	private String userName;
	private String userNick;
	private String userInfo;
	private String userEmail;
	private int userLevel;
	private int warningCount;
	private String createDate;
	private String updateDate;
	//private List<UserImg> imgList;
	
	//검색기능
	private String keyword;
	//팔로잉 아이디
	private String followingId;
	private String userId;
	//여러개 담을수있는것
	private List<Photo> photolist;
	private List<Feed>	feedlist;
	private String feedWriter;
	private String userFeedFilePath;
}
