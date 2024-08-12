package kr.or.iei.user.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
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
	private String userImg;
	private String cancelDate;
}
