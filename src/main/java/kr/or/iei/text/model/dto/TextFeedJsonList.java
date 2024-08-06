package kr.or.iei.text.model.dto;

import kr.or.iei.user.model.dto.User;
import kr.or.iei.text.model.dto.TextFeed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TextFeedJsonList {
	private User user;
	private TextFeed textFeed;
	//추후 추가
	//private String userImgJson;
}
