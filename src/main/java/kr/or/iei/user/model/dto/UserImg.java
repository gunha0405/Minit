package kr.or.iei.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserImg {
	private int imgNo;
	private int userNo;
	private String imgOrg;
	private String imgStorage;
	private int imgType;
	private String createDate;
	private String updateDate;
}